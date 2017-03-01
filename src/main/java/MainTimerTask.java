import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


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
        Timer timer = new Timer(true);
        Date currDate = new Date((System.currentTimeMillis() / 1000) * 1000);
        currDate.setSeconds(0);
        currDate.setHours(0);
        currDate.setMinutes(0);

        for (Event event :
                LocalStore.getInstance().getEventsList()) {
            Date eventDateWithoutTime = new Date(event.getDate().getTime());
            eventDateWithoutTime.setSeconds(0);
            eventDateWithoutTime.setHours(0);
            eventDateWithoutTime.setMinutes(0);
            if (eventDateWithoutTime.equals(currDate)) {
                timer.schedule(new EventTimerTask(event), event.getDate());
            }
        }
    }
}
