package implementations;


import factories.LocalStoreFactory;
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
                default:
                    return Command.NOTHING;
            }
        }
        return Command.NOTHING;
    }

    private void doStartCommand() {
        InMemoryLocalStore.getInstance().userRegistration(update);
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
            List<Event> events = new ArrayList(); // TODO implementations.EventParserImpl.getInstance().parse(update);
            if (!events.isEmpty()) {
                InMemoryLocalStore.getInstance().addEvents(events);
                bot.sendMessage(chat.getId(), Strings.EVENTS_ADDED);
            }

            chat.setPreviousCommand(Command.NOTHING);
        }
    }


    public void pollingUpdates() {

        while (true) {
            update = updateQueue.poll();
            if (updateQueue != null) {
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
