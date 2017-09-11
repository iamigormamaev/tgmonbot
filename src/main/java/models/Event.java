package models;

import org.telegram.telegrambots.api.objects.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private User author;
    private User user;
    private Date date;
    private String message;
    private boolean isStarted;
    private boolean isFinished;
    private boolean isActive;


    public Event(User author, User user, Date date, String message) {
        this.author = author;
        this.user = user;
        this.date = date;
        this.message = message;
        isStarted = false;
        isFinished = false;
        isActive = true;

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public String getMessage() {
        return message;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (isStarted != event.isStarted) return false;
        if (isFinished != event.isFinished) return false;
        if (user != null ? !user.equals(event.user) : event.user != null) return false;
        return date != null ? date.equals(event.date) : event.date == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (isStarted ? 1 : 0);
        result = 31 * result + (isFinished ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
/*        return "Event{" +
                "author=" + author +
                ", user=" + user +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", isStarted=" + isStarted +
                ", isFinished=" + isFinished +
                '}';*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return user.getFirstName() + " " + user.getLastName() + " (" + user.getUserName() + ") " + " " + dateFormat.format(date) + " " + message;
    }
}
