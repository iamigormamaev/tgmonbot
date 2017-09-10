package implementations;


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
import java.util.Queue;


public class UpdateHandlerImpl implements UpdateHandler {

    private LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();
    private Queue<Update> updateQueue = localStore.getUpdateQueue();
    private Update update = null;
    private ChatWithCommand chat = null;
    private TcsMonitoringBot bot = Main.getTcsMonitoringBot();
    private EventParser eventParser = new EventParserImpl(localStore.getRegisteredUsers());

    @Override
    public void handleUpdate(Update update, ChatWithCommand chat) {
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

    private void doStartCommand() {
        CollectionsLocalStore.getInstance().userRegistration(update);
    }

    private void doAddCommand() {
        bot.sendMessage(update.getMessage().getChatId(), Strings.ADD_EVENT);
        chat.setPreviousCommand(Command.ADD);
    }

    private void doDeleteCommand() {
        chat.setPreviousCommand(Command.DELETE);
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
                }
            }
            if (!events.isEmpty()) {
                CollectionsLocalStore.getInstance().addEvents(events);
                bot.sendMessage(chat.getId(), Strings.EVENTS_ADDED);
            }
            chat.setPreviousCommand(Command.NOTHING);
        }
    }

    private void doListCommand() {
        String fullMessageString = "Список созданных вами напоминаний (уже отработавшие не включены):\n";
        for (Event e :
                localStore.getEventsListFilterByAuthor(update.getMessage().getFrom())) {
            fullMessageString = fullMessageString + e + "\n";
        }
        bot.sendMessage(chat.getId(), fullMessageString);
    }


    public void pollingUpdates() {

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (updateQueue.peek() != null) {
                update = updateQueue.poll();
                System.out.println(update);
                if (update.hasMessage() && update.getMessage().hasText() && !localStore.getChats().containsKey(update.getMessage().getChatId())) {
                    localStore.getChats().put(update.getMessage().getChatId(), new ChatWithCommand(update.getMessage().getChat()));
                    chat = localStore.getChats().get(update.getMessage().getChatId());
                    handleUpdate(update, chat);
                } else {
                    chat = localStore.getChats().get(update.getMessage().getChatId());
                    handleUpdate(update, localStore.getChats().get(update.getMessage().getChatId()));
                }
            }

        }
    }
}
