package implementations;

import factories.LocalStoreFactory;
import interfaces.LocalStore;
import models.Event;
import models.EventStatus;

import java.util.*;
import java.util.logging.Logger;


public class MainTimerTask extends TimerTask {

    private final static Logger LOGGER = Logger.getLogger(MainTimerTask.class.getName());

    public void run() {
        LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();
        LOGGER.info("main timer task starts");
        Timer timer = new Timer(true);
        Date currDate = new Date((System.currentTimeMillis() / 1000) * 1000);
        currDate.setSeconds(0);
        currDate.setHours(0);
        currDate.setMinutes(0);
        List<Event> tempEventsForWork = new ArrayList<>();
        tempEventsForWork.addAll(localStore.getEvents(EventStatus.NEW));
        for (Event event :
                tempEventsForWork) {
            if (event.getStatus() == EventStatus.NEW) {
                Date eventDateWithoutTime = new Date(event.getDate().getTime());
                eventDateWithoutTime.setSeconds(0);
                eventDateWithoutTime.setHours(0);
                eventDateWithoutTime.setMinutes(0);
                if (eventDateWithoutTime.equals(currDate) && event.getDate().after(new Date(System.currentTimeMillis()))) {
                    timer.schedule(new EventTimerTask(event), event.getDate());
                }
            }
        }
    }
}
