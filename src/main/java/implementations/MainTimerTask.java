package implementations;

import java.util.*;


public class MainTimerTask extends TimerTask {

    public void run() {
/*        try {
            Update update = implementations.Main.getMyMonitoringBot().getUpdate();
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText("lol " + System.currentTimeMillis());
            implementations.Main.getMyMonitoringBot().sendMessage(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
        Timer timer = new Timer(true);
        Date currDate = new Date((System.currentTimeMillis() / 1000) * 1000);
        currDate.setSeconds(0);
        currDate.setHours(0);
        currDate.setMinutes(0);


        List<Event> tempEventsForWork = new ArrayList<>();
        tempEventsForWork.addAll(InMemoryLocalStore.getInstance().getEventsList());


        for (Event event :
                tempEventsForWork) {
            Date eventDateWithoutTime = new Date(event.getDate().getTime());
            eventDateWithoutTime.setSeconds(0);
            eventDateWithoutTime.setHours(0);
            eventDateWithoutTime.setMinutes(0);
            if (eventDateWithoutTime.equals(currDate) && event.getDate().after(new Date(System.currentTimeMillis()))) {
                timer.schedule(new EventTimerTask(event), event.getDate());
                InMemoryLocalStore.getInstance().deleteEvent(event);
            }
        }
    }
}
