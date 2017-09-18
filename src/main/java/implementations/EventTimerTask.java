package implementations;

import factories.LocalStoreFactory;
import interfaces.LocalStore;
import models.Event;

import java.util.TimerTask;
import java.util.logging.Logger;

public class EventTimerTask extends TimerTask {
    private final static Logger LOGGER = Logger.getLogger(EventTimerTask.class.getName());
    private Event event;
    private TcsMonitoringBot bot = Main.getTcsMonitoringBot();
    private LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();

    public EventTimerTask(Event event) {
        this.event = event;
        event.setStarted(true);
        LOGGER.info("Event timer starts: " + event);
    }

    @Override
    public void run() {
        LOGGER.info("Event timer runs: " + event.getMessageWithAuthor());
        if (event.isActive()) {
            if (event.getUser().getId().equals(event.getAuthor().getId()))
                bot.sendMessage(localStore.getChatByUser(event.getUser()).getId(), event.getMessageWithoutAuthor());
            else
                bot.sendMessage(localStore.getChatByUser(event.getUser()).getId(), event.getMessageWithAuthor());
            event.setFinished(true);
        }
    }
}
