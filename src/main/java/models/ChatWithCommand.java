package models;

import org.telegram.telegrambots.api.objects.Chat;

import javax.persistence.*;


@Entity
@Table(name = "chats", schema = "reminder_bot")
public class ChatWithCommand {
    private Command previousCommand;
    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean allMembersAreAdministrators;
    private User user;

    public ChatWithCommand(Chat chat, User user) {
        this.user = user;
        id = chat.getId();
        title = chat.getTitle();
        firstName = chat.getFirstName();
        lastName = chat.getLastName();
        userName = chat.getUserName();

        allMembersAreAdministrators = chat.getAllMembersAreAdministrators();
        previousCommand = Command.NOTHING;
    }

    public ChatWithCommand() {
    }

    @Id
    @Column(name = "chat_id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne (cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 100)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 100)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "all_members_are_administrators", nullable = true)
    public Boolean getAllMembersAreAdministrators() {
        return allMembersAreAdministrators;
    }

    public void setAllMembersAreAdministrators(Boolean allMembersAreAdministrators) {
        this.allMembersAreAdministrators = allMembersAreAdministrators;
    }

    @Basic
    @Column(name = "previous_command", nullable = false)
    @Enumerated(EnumType.STRING)
    public Command getPreviousCommand() {
        return previousCommand;
    }

    public void setPreviousCommand(Command previousCommand) {
        this.previousCommand = previousCommand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatWithCommand that = (ChatWithCommand) o;

        if (previousCommand != that.previousCommand) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        return allMembersAreAdministrators != null ? allMembersAreAdministrators.equals(that.allMembersAreAdministrators) : that.allMembersAreAdministrators == null;
    }

    @Override
    public int hashCode() {
        int result = previousCommand != null ? previousCommand.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (allMembersAreAdministrators != null ? allMembersAreAdministrators.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChatWithCommand{" +
                "previousCommand=" + previousCommand +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", allMembersAreAdministrators=" + allMembersAreAdministrators +
                ", user=" + user +
                '}';
    }
}

