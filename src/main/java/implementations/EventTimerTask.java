package implementations;

import models.Event;

import java.util.TimerTask;

public class EventTimerTask extends TimerTask {
    Event event;

    public EventTimerTask(Event event) {
        this.event = event;
        System.out.println("New models.Event timer starts: " + event.getUser().getUserName() + " " + event.getDate());
    }

    @Override
    public void run() {
        Main.getTcsMonitoringBot().sendMessage(InMemoryLocalStore.getInstance().getUsersToChat().get(event.getUser()).getId(), "Завтра ты дежуришь с 9:00!");
    }
}
