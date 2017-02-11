import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Timer;


public class MyMonitoringBot extends TelegramLongPollingBot {
    private Update update;

    public void onUpdateReceived(Update update) {
        myOnUpdateReceived(update);

        /*        System.out.println(update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            this.update = update;
            Timer timer = new Timer(true);
            timer.schedule(new MainTimerTask(), 0, 3000);

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
        return Config.botName;
    }

    public String getBotToken() {
        return Config.botToken;
    }

    public Update getUpdate() {
        return update;
    }

    private void myOnUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String command = update.getMessage().getText();
            if (!(command.indexOf(" ")==-1)) command=command.substring(0, update.getMessage().getText().indexOf(" "));

            System.out.println(command);
            switch (command) {
                case "/start":
                    LocalStore.getInstance().userRegistration(update);
                    break;
                case "/add":
                    break;
                case "/delete":
                    break;
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
