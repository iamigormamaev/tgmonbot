package implementations;


import Exceptions.DateFromPastException;
import Exceptions.DateParseException;
import Exceptions.NotEnoughArgsToParseException;
import Exceptions.NotRegisteredUserException;
import factories.LocalStoreFactory;
import interfaces.EventParser;
import interfaces.LocalStore;
import interfaces.Strings;
import interfaces.UpdateHandler;
import models.ChatWithCommand;
import models.Event;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;


public class UpdateHandlerImpl implements UpdateHandler {

    private LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();
    private Update update = null;
    private ChatWithCommand chat = null;
    private TcsMonitoringBot bot = Main.getTcsMonitoringBot();
    private EventParser eventParser = new EventParserImpl();

    @Override
    public void handleUpdate(Update update, ChatWithCommand chat) {
        if (chat.getPreviousCommand() == Command.DELETE) {
            doAfterDeleteCommand();
        } else {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Command command = checkCommand(update.getMessage().getText());
                System.out.println(command);
                switch (command) {
                    case START:
                        doStartCommand();
                        break;
                    case ADD:
                        doAddCommand();
                        break;
                    case DELETE:
                        doDeleteCommand();
                        break;
                    case LIST:
                        doListCommand();
                        break;
                    default:
                        doNothingCommand();
                }
            }
        }
    }


    private Command checkCommand(String string) {
        if (string.contains("/")) {
            String stringCommand = string.substring(string.indexOf("/"), string.indexOf(' ') == -1 ? string.length() : string.indexOf(' ')).toLowerCase();
            switch (stringCommand) {
                case "/start":
                    return Command.START;
                case "/add":
                    return Command.ADD;
                case "/delete":
                    return Command.DELETE;
                case "/list":
                    return Command.LIST;
                default:
                    return Command.NOTHING;
            }
        }
        return Command.NOTHING;
    }

    private int checkNumberCommand(String string) {
        if (string.contains("/")) {
            String stringCommand = string.substring(string.indexOf("/") + 1, string.indexOf(' ') == -1 ? string.length() : string.indexOf(' ')).toLowerCase();
            return Integer.parseInt(stringCommand);
        }
        throw new NumberFormatException();
    }

    private void doStartCommand() {

        if (!localStore.isAlreadyRegisteredUser(update.getMessage().getFrom())) {
            localStore.userRegistration(update.getMessage().getFrom(), new ChatWithCommand(update.getMessage().getChat()));
            if (update.getMessage().getFrom().getUserName() == null) {
                bot.sendMessage(update.getMessage().getChatId(), Strings.SUCCESSFUL_REGISTRATION_WITHOUT_USERNAME);
            } else
                bot.sendMessage(update.getMessage().getChatId(), Strings.SUCCESSFUL_REGISTRATION);
        } else {
            bot.sendMessage(update.getMessage().getChatId(), Strings.ALREADY_REGISTERED);
        }
    }

    private void doAddCommand() {
        bot.sendMessage(update.getMessage().getChatId(), Strings.ADD_EVENT);
        chat.setPreviousCommand(Command.ADD);
    }

    private void doDeleteCommand() {
        chat.setPreviousCommand(Command.DELETE);
        List<Event> eventList = localStore.getEventsListFilterByAuthor(update.getMessage().getFrom());
        chat.setEventListForDeleteCommand(eventList);
        bot.sendMessage(chat.getId(), Strings.DELETE_TEXT + createListOfEvents(eventList, true));
    }

    private void doAfterDeleteCommand() {
        try {
            int eventNumber = checkNumberCommand(update.getMessage().getText());
            Event eventForDelete = chat.getEventListForDeleteCommand().get(eventNumber);
            localStore.deleteEvent(eventForDelete);
            bot.sendMessage(chat.getId(), Strings.SUCCESSFUL_DELETE + "" + eventForDelete);
            chat.setEventListForDeleteCommand(null);
            chat.setPreviousCommand(Command.NOTHING);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            bot.sendMessage(chat.getId(), Strings.CANT_DELETE_EVENT);
            chat.setPreviousCommand(Command.NOTHING);
        }
    }

    private void doNothingCommand() {
        if (chat.getPreviousCommand() == Command.ADD || chat.getPreviousCommand() == Command.NOTHING) {
            String[] inStrings = update.getMessage().getText().split("\n");
            List<Event> events = new ArrayList<>();
            for (String s :
                    inStrings) {
                try {
                    Event e = eventParser.parse(s, update.getMessage().getFrom());
                    events.add(e);
                } catch (NotEnoughArgsToParseException e1) {
                    bot.sendMessage(chat.getId(), Strings.WRONG_EVENT + s);
                } catch (NotRegisteredUserException e1) {
                    bot.sendMessage(chat.getId(), Strings.NOT_REGISTERED_USER + s);
                } catch (DateParseException e1) {
                    bot.sendMessage(chat.getId(), Strings.CANT_PARSE_DATE + s);
                } catch (DateFromPastException e) {
                    bot.sendMessage(chat.getId(), Strings.DATE_FROM_PAST + s);
                }
            }
            if (!events.isEmpty()) {
                localStore.addEvents(events);
                bot.sendMessage(chat.getId(), Strings.EVENTS_ADDED);
            }
            chat.setPreviousCommand(Command.NOTHING);
        }
    }

    private void doListCommand() {
        String listOfEventsString = createListOfEvents(localStore.getEventsListFilterByAuthor(update.getMessage().getFrom()), false);
        if (!listOfEventsString.equals(""))
            bot.sendMessage(chat.getId(), Strings.LIST_TEXT + listOfEventsString);
        else
            bot.sendMessage(chat.getId(), Strings.EMPTY_LIST_OF_EVENTS);
    }


    public void pollingUpdates() {

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (localStore.updateQueuePeek() != null) {
                update = localStore.updateQueuePool();
                System.out.println(update);
                if (update.hasMessage() && update.getMessage().hasText() && !localStore.containsChatById(update.getMessage().getChatId())) {
                    localStore.putToChats(update.getMessage().getChatId(), new ChatWithCommand(update.getMessage().getChat()));
                    chat = localStore.getChatById(update.getMessage().getChatId());
                    handleUpdate(update, chat);
                } else {
                    chat = localStore.getChatById(update.getMessage().getChatId());
                    handleUpdate(update, localStore.getChatById(update.getMessage().getChatId()));
                }
            }

        }
    }

    private String createListOfEvents(List<Event> eventList, boolean addEventsIDs) {
        String resultString = "";
        for (int i = 0; i < eventList.size(); i++) {
            if (addEventsIDs) {
                resultString = resultString + "/" + i + " " + eventList.get(i) + "\n";
            } else resultString = resultString + eventList.get(i) + "\n";
        }
        return resultString;
    }
}
