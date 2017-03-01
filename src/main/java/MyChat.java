import org.telegram.telegrambots.api.objects.Chat;

/**
 * Created by Игорь on 28.02.2017.
 */
public class MyChat {
    private Chat chat;
    private Command previousCommand;

    public MyChat(Chat chat) {
        this.chat = chat;
        previousCommand = Command.NOTHING;
    }

    public Long getId() {
        return chat.getId();
    }

    public Boolean isGroupChat() {
        return chat.isGroupChat();
    }

    public Boolean isChannelChat() {
        return chat.isChannelChat();
    }

    public Boolean isUserChat() {
        return chat.isUserChat();
    }

    public Boolean isSuperGroupChat() {
        return chat.isSuperGroupChat();
    }

    public String getTitle() {
        return chat.getTitle();
    }

    public String getFirstName() {
        return chat.getFirstName();
    }

    public String getLastName() {
        return chat.getLastName();
    }

    public String getUserName() {
        return chat.getUserName();
    }

    public Boolean getAllMembersAreAdministrators() {
        return chat.getAllMembersAreAdministrators();
    }

    public Command getPreviousCommand() {
        return previousCommand;
    }

    public void setPreviousCommand(Command previousCommand) {
        this.previousCommand = previousCommand;
    }
}

