import org.telegram.telegrambots.api.objects.User;

import java.util.Date;

public class Event {
    private User user;
    private Date date;
    private boolean isStarted;
    private boolean isFinished;

    public Event(User user, Date date) {
        this.user = user;
        this.date = date;
        isStarted = false;
        isFinished = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        return user.toString() + " " + date;
    }
}
