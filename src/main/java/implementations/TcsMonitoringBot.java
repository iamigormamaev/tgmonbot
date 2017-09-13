package implementations;

import factories.LocalStoreFactory;
import interfaces.Config;
import interfaces.LocalStore;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.util.Queue;
import java.util.Timer;


public class TcsMonitoringBot extends TelegramLongPollingBot {

    private LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();

    public TcsMonitoringBot() {

        Timer timer = new Timer(true);
//        timer.schedule(new implementations.MainTimerTask(), 0, 86400000);
    }

    public void onUpdateReceived(Update update) {
        localStore.updateQueueAdd(update);
    }

    public String getBotUsername() {
        return Config.BOT_NAME;
    }

    public String getBotToken() {
        return Config.BOT_TOKEN;
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
