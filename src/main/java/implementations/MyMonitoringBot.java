package implementations;

import interfaces.Config;
import interfaces.Strings;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class MyMonitoringBot extends TelegramLongPollingBot {

    public MyMonitoringBot() {
        Timer timer = new Timer(true);
//        timer.schedule(new implementations.MainTimerTask(), 0, 86400000);
    }

    public void onUpdateReceived(Update update) {


        if (update.hasMessage() &&
                update.getMessage().hasText() &&
                !InMemoryLocalStore.getInstance().getChats().containsKey(update.getMessage().getChatId())) {
            InMemoryLocalStore.getInstance().getChats().put(update.getMessage().getChatId(), new MyChat(update.getMessage().getChat()));
            myOnUpdateReceived(update, InMemoryLocalStore.getInstance().getChats().get(update.getMessage().getChatId()));
        }
        else myOnUpdateReceived(update, InMemoryLocalStore.getInstance().getChats().get(update.getMessage().getChatId()));



        /*        System.out.println(update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            this.update = update;
            Timer timer = new Timer(true);
            timer.schedule(new implementations.MainTimerTask(), 0, 3000);

        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            try {
                sendMessage(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }*/
    }

    public String getBotUsername() {
        return Config.BOT_NAME;
    }

    public String getBotToken() {
        return Config.BOT_TOKEN;
    }


    private void myOnUpdateReceived(Update update, MyChat myChat) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String command = update.getMessage().getText();
            if ((command.contains(" ")))
                command = command.substring(0, update.getMessage().getText().indexOf(" "));

            System.out.println(command);
            switch (command) {
                case "/start":
                    InMemoryLocalStore.getInstance().userRegistration(update);
                    break;
                case "/add":
                    sendMessage(update.getMessage().getChatId(), Strings.ADD_EVENT);
                    myChat.setPreviousCommand(Command.ADD);
                    break;
                case "/delete":
                    break;
                default:
                    if (myChat.getPreviousCommand() == Command.ADD || myChat.getPreviousCommand() == Command.NOTHING) {
                        List<Event> events = new ArrayList(); // TODO implementations.EventParserImpl.getInstance().parse(update);
                        if (!events.isEmpty()) {
                            InMemoryLocalStore.getInstance().addEvents(events);
                            sendMessage(myChat.getId(), Strings.EVENTS_ADDED);
                        }

                        myChat.setPreviousCommand(Command.NOTHING);
                    }
            }
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(chatId)
                .setText(text);
        try {
            sendMessage(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
