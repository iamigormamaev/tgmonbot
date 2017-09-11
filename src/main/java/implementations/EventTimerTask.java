package implementations;

import factories.LocalStoreFactory;
import interfaces.LocalStore;
import models.Event;

import java.util.TimerTask;

public class EventTimerTask extends TimerTask {
    private Event event;
    private TcsMonitoringBot bot = Main.getTcsMonitoringBot();
    private LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();

    public EventTimerTask(Event event) {
        this.event = event;
        event.setStarted(true);
        System.out.println("Event timer starts: " + event.getUser().getUserName() + " " + event.getDate());
    }

    @Override
    public void run() {
        if (event.isActive()) {
            if (event.getUser().getId().equals(event.getAuthor().getId()))
                bot.sendMessage(localStore.getUsersToChat().get(event.getUser()).getId(), event.getMessage());
            else
                bot.sendMessage(localStore.getUsersToChat().get(event.getUser()).getId(),
                        event.getAuthor().getFirstName() + " " + event.getAuthor().getLastName() + ": " + event.getMessage());
            event.setFinished(true);
        }
    }
}
