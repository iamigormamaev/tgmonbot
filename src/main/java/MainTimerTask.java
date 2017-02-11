import java.util.TimerTask;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import org.telegram.telegrambots.exceptions.TelegramApiException;


public class MainTimerTask extends TimerTask {

    public void run() {
/*        try {
            Update update = Main.getMyMonitoringBot().getUpdate();
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText("lol " + System.currentTimeMillis());
            Main.getMyMonitoringBot().sendMessage(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
    }
}
