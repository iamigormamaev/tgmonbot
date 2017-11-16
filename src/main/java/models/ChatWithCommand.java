package models;

import implementations.Command;
import org.telegram.telegrambots.api.objects.Chat;

import java.util.List;

public class ChatWithCommand {
    private Command previousCommand;
    private List<Event> eventListForDeleteCommand = null;
    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean allMembersAreAdministrators;

    public ChatWithCommand(Chat chat) {

        id = chat.getId();
        title = chat.getTitle();
        firstName = chat.getFirstName();
        lastName = chat.getLastName();
        userName = chat.getUserName();
        allMembersAreAdministrators = chat.getAllMembersAreAdministrators();
        previousCommand = Command.NOTHING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getAllMembersAreAdministrators() {
        return allMembersAreAdministrators;
    }

    public void setAllMembersAreAdministrators(Boolean allMembersAreAdministrators) {
        this.allMembersAreAdministrators = allMembersAreAdministrators;
    }

    public Command getPreviousCommand() {
        return previousCommand;
    }

    public void setPreviousCommand(Command previousCommand) {
        this.previousCommand = previousCommand;
    }

    public List<Event> getEventListForDeleteCommand() {
        return eventListForDeleteCommand;
    }

    public void setEventListForDeleteCommand(List<Event> eventListForDeleteCommand) {
        this.eventListForDeleteCommand = eventListForDeleteCommand;
    }
}

