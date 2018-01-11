package implementations;

import factories.LocalStoreFactory;
import interfaces.Config;
import interfaces.LocalStore;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


public class TcsMonitoringBot extends TelegramLongPollingBot {

    private LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();

    public TcsMonitoringBot() {
        localStore.initStore();
        Timer timer = new Timer(true);
        new MainTimerTask().run();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        Date tomorrow = c.getTime();

        timer.schedule(new MainTimerTask(), tomorrow, 86400000);

    }

    public void onUpdateReceived(Update update) {
        localStore.updateQueueAdd(new models.Update(update));
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
