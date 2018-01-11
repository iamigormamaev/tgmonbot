package models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "events", schema = "reminder_bot")
public class Event {
    private Integer id;
    private User author;
    private User user;
    private Date date;
    private String message;
    private EventStatus status;


    public Event() {
    }

    public Event(User author, User user, Date date, String message) {
        this.author = author;
        this.user = user;
        this.date = date;
        this.message = message;
        status = EventStatus.NEW;
    }

    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    @Id
    @Column(name = "id", nullable = true)
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }





    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "message", nullable = true)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (author != null ? !author.equals(event.author) : event.author != null) return false;
        if (user != null ? !user.equals(event.user) : event.user != null) return false;
        if (date != null ? !date.equals(event.date) : event.date != null) return false;
        if (message != null ? !message.equals(event.message) : event.message != null) return false;
        return status == event.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public String toBeautifulString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String lastName = user.getLastName() == null ? "" : user.getLastName();
        String userName = user.getUserName() == null ? "" : " (" + user.getUserName() + ") ";
        return user.getFirstName() + " " + lastName + userName + dateFormat.format(date) + " " + message;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", author=" + author +
                ", user=" + user +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
