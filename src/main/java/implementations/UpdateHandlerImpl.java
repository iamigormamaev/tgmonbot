package implementations;


import Exceptions.*;
import factories.LocalStoreFactory;
import interfaces.EventParser;
import interfaces.LocalStore;
import interfaces.Strings;
import interfaces.UpdateHandler;
import models.ChatWithCommand;
import models.Command;
import models.Event;
import models.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UpdateHandlerImpl implements UpdateHandler {

    private LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();
    private Update update = null;
    private ChatWithCommand chat = null;
    private TcsMonitoringBot bot = Main.getTcsMonitoringBot();
    private EventParser eventParser = new EventParserImpl();
    private final static Logger LOGGER = Logger.getLogger(UpdateHandlerImpl.class.getName());

    @Override
    public void handleUpdate(Update update, ChatWithCommand chat) {
        LOGGER.info("handling update: " + update.getMessage());
        if (chat.getPreviousCommand() == Command.DELETE) {
            doAfterDeleteCommand();
        } else {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Command command = checkCommand(update.getMessage().getText());
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
                    case HELP:
                        doHelpCommand();
                        break;
                    default:
                        doNothingCommand();
                }
            }
        }
    }

    private void doHelpCommand() {
        LOGGER.info("doHelpCommand");
        bot.sendMessage(chat.getId(), Strings.HELP);
        LOGGER.info("LIST_TEXT_SUCCESSFUL_SEND");

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
                case "/help":
                    return Command.HELP;
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
        LOGGER.info("doStartCommand");
        if (!localStore.isAlreadyRegisteredUser(update.getMessage().getFrom())) {
            localStore.userRegistration(update.getMessage().getFrom(), new ChatWithCommand(update.getMessage().getChat(), update.getMessage().getFrom()));
            if (update.getMessage().getFrom().getUserName() == null) {
                bot.sendMessage(update.getMessage().getChatId(), Strings.SUCCESSFUL_REGISTRATION_WITHOUT_USERNAME);
                LOGGER.info("SUCCESSFUL_REGISTRATION_WITHOUT_USERNAME");
            } else {
                localStore.addOrUpdateChat(
                        update.getMessage().getChatId(),
                        new ChatWithCommand(
                                update.getMessage().getChat(),
                                update.getMessage().getFrom()
                        )
                );
                bot.sendMessage(update.getMessage().getChatId(), Strings.SUCCESSFUL_REGISTRATION);
                LOGGER.info("SUCCESSFUL_REGISTRATION");
            }
        } else {
            bot.sendMessage(update.getMessage().getChatId(), Strings.ALREADY_REGISTERED);
            LOGGER.info("ALREADY_REGISTERED");
        }
    }

    private void doAddCommand() {
        LOGGER.info("doAddCommand");
        bot.sendMessage(update.getMessage().getChatId(), Strings.ADD_EVENT);
        localStore.setChatPreviousCommand(chat, Command.ADD);
    }

    private void doDeleteCommand() {
        LOGGER.info("doDeleteCommand");
        localStore.setChatPreviousCommand(chat, Command.DELETE);
        List<Event> eventList = localStore.getEventsListFilterByAuthor(update.getMessage().getFrom());
//        chat.setEventListForDeleteCommand(eventList);
        if (!eventList.isEmpty()) {
            bot.sendMessage(chat.getId(), Strings.DELETE_TEXT + createListOfEvents(eventList, true));
        } else {
            localStore.setChatPreviousCommand(chat, Command.NOTHING);
            bot.sendMessage(chat.getId(), Strings.EMPTY_LIST_OF_EVENTS);
        }

    }

    private void doAfterDeleteCommand() {
        try {
            LOGGER.info("doAfterDeleteCommand");
            int eventNumber = checkNumberCommand(update.getMessage().getText());
            Event eventForDelete = localStore.getEventsListFilterByAuthor(update.getMessage().getFrom()).get(eventNumber);
            localStore.finishEvent(eventForDelete);
            bot.sendMessage(chat.getId(), Strings.SUCCESSFUL_DELETE + "" + eventForDelete.toBeautifulString());
            LOGGER.info("SUCCESSFUL_DELETE");
//            chat.setEventListForDeleteCommand(null);
            localStore.setChatPreviousCommand(chat, Command.NOTHING);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            bot.sendMessage(chat.getId(), Strings.CANT_DELETE_EVENT);
            LOGGER.info("CANT_DELETE_EVENT");
            localStore.setChatPreviousCommand(chat, Command.NOTHING);
        }
    }

    private void doNothingCommand() {
        LOGGER.info("doNothingCommand");
        if (chat.getPreviousCommand() == Command.ADD || chat.getPreviousCommand() == Command.NOTHING) {
            String[] inStrings = update.getMessage().getText().split("\n");
            List<Event> events = new ArrayList<>();
            for (String s :
                    inStrings) {
                try {
                    Event e = eventParser.parse(s, update.getMessage().getFrom());
                    events.add(e);
                } catch (NotEnoughArgsToParseException e1) {
                    bot.sendMessage(chat.getId(), Strings.WRONG_EVENT + s + Strings.HELP_ANNOUNCEMENT);
                    LOGGER.info("WRONG_EVENT");
                } catch (NotRegisteredUserException e1) {
                    bot.sendMessage(chat.getId(), Strings.NOT_REGISTERED_USER + s);
                    LOGGER.info("NOT_REGISTERED_USER");
                } catch (DateParseException e1) {
                    bot.sendMessage(chat.getId(), Strings.CANT_PARSE_DATE + s + Strings.HELP_ANNOUNCEMENT);
                    LOGGER.info("CANT_PARSE_DATE");
                } catch (DateFromPastException e) {
                    LOGGER.info("DATE_FROM_PAST");
                    bot.sendMessage(chat.getId(), Strings.DATE_FROM_PAST + s);
                }
            }
            if (!events.isEmpty()) {
                try {
                    localStore.addEvents(events);


                    bot.sendMessage(chat.getId(), Strings.EVENTS_ADDED);
                    LOGGER.info("EVENTS_ADDED");
                } catch (DbProblemException e) {
                    bot.sendMessage(chat.getId(), Strings.DB_PROBLEMS);
                    LOGGER.info("EVENTS_DONT_ADDED_EXCEPTION");
                }
            }
            localStore.setChatPreviousCommand(chat, Command.NOTHING);

        }
    }

    private void doListCommand() {
        LOGGER.info("doListCommand");
        String listOfEventsString = createListOfEvents(localStore.getEventsListFilterByAuthor(update.getMessage().getFrom()), false);
        if (!listOfEventsString.equals("")) {
            bot.sendMessage(chat.getId(), Strings.LIST_TEXT + listOfEventsString);
            LOGGER.info("LIST_TEXT_SUCCESSFUL_SEND");
        } else {
            bot.sendMessage(chat.getId(), Strings.EMPTY_LIST_OF_EVENTS);
            LOGGER.info("EMPTY_LIST_OF_EVENTS");
        }
    }


    public void pollingUpdates() {

        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (localStore.updateQueuePeek() != null) {
                update = localStore.updateQueuePool();
                if (update.hasMessage() &&
                        update.getMessage().hasText() &&
                        !localStore.containsChatById(update.getMessage().getChatId())) {
                    localStore.addOrUpdateChat(
                            update.getMessage().getChatId(),
                            new ChatWithCommand(
                                    update.getMessage().getChat(),
                                    update.getMessage().getFrom()
                            )
                    );
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
                resultString = resultString + "/" + i + " " + eventList.get(i).toBeautifulString() + "\n";
            } else resultString = resultString + eventList.get(i).toBeautifulString() + "\n";
        }
        return resultString;
    }
}
