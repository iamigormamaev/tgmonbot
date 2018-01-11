package implementations;

import factories.LocalStoreFactory;
import interfaces.LocalStore;
import models.Event;
import models.EventStatus;

import java.util.TimerTask;
import java.util.logging.Logger;

public class EventTimerTask extends TimerTask {
    private final static Logger LOGGER = Logger.getLogger(EventTimerTask.class.getName());
    private Event event;

    private LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();

    public EventTimerTask(Event event) {
        this.event = event;
        localStore.startEvent(this.event);
        LOGGER.info("Event timer starts: " + event);
    }

    @Override
    public void run() {
        TcsMonitoringBot bot = Main.getTcsMonitoringBot();
        String eventMessageWithAuthor = event.getAuthor().getFirstName() + " " + event.getUser().getLastName() + event.getUser().getFirstName() + ": " + event.getMessage();
        LOGGER.info("Event timer runs: " + eventMessageWithAuthor);
        if (event.getStatus() != EventStatus.FINISHED) {
            if (event.getUser().getId() == (event.getAuthor().getId())) {
                bot.sendMessage(localStore.getChatByUser(event.getUser()).getId(), event.getMessage());
            } else {
                bot.sendMessage(localStore.getChatByUser(event.getUser()).getId(), eventMessageWithAuthor);
            }
            localStore.finishEvent(event);
        }
    }
}
