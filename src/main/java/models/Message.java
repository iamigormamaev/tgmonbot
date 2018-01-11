package models;

import org.telegram.telegrambots.api.objects.*;
import org.telegram.telegrambots.api.objects.games.Game;

import java.util.List;

public class Message {
    private org.telegram.telegrambots.api.objects.Message message;
    private User from;
    private User forwardFrom;
    private User newChatMember;
    private User leftChatMember;

    public Message(org.telegram.telegrambots.api.objects.Message message) {
        this.message = message;
        from = new User(message.getFrom());
        forwardFrom = message.getForwardFrom() != null ? new User(message.getForwardFrom()) : null;
        newChatMember = message.getNewChatMember() != null ? new User(message.getNewChatMember()) : null;
        leftChatMember = message.getLeftChatMember() != null ? new User(message.getLeftChatMember()) : null;

    }

    public Integer getMessageId() {
        return message.getMessageId();
    }

    public User getFrom() {
        return from;
    }

    public Integer getDate() {
        return message.getDate();
    }

    public Chat getChat() {
        return message.getChat();
    }

    public User getForwardFrom() {
        return forwardFrom;
    }

    public Integer getForwardDate() {
        return message.getForwardDate();
    }

    public String getText() {
        return message.getText();
    }

    public List<MessageEntity> getEntities() {
        return message.getEntities();
    }

    public Audio getAudio() {
        return message.getAudio();
    }

    public Document getDocument() {
        return message.getDocument();
    }

    public List<PhotoSize> getPhoto() {
        return message.getPhoto();
    }

    public Sticker getSticker() {
        return message.getSticker();
    }

    public Video getVideo() {
        return message.getVideo();
    }

    public Contact getContact() {
        return message.getContact();
    }

    public Location getLocation() {
        return message.getLocation();
    }

    public Venue getVenue() {
        return message.getVenue();
    }

    public org.telegram.telegrambots.api.objects.Message getPinnedMessage() {
        return message.getPinnedMessage();
    }

    public User getNewChatMember() {
        return newChatMember;
    }

    public User getLeftChatMember() {
        return leftChatMember;
    }

    public String getNewChatTitle() {
        return message.getNewChatTitle();
    }

    public List<PhotoSize> getNewChatPhoto() {
        return message.getNewChatPhoto();
    }

    public Boolean getDeleteChatPhoto() {
        return message.getDeleteChatPhoto();
    }

    public Boolean getGroupchatCreated() {
        return message.getGroupchatCreated();
    }

    public org.telegram.telegrambots.api.objects.Message getReplyToMessage() {
        return message.getReplyToMessage();
    }

    public Voice getVoice() {
        return message.getVoice();
    }

    public String getCaption() {
        return message.getCaption();
    }

    public Boolean getSuperGroupCreated() {
        return message.getSuperGroupCreated();
    }

    public Boolean getChannelChatCreated() {
        return message.getChannelChatCreated();
    }

    public Long getMigrateToChatId() {
        return message.getMigrateToChatId();
    }

    public Long getMigrateFromChatId() {
        return message.getMigrateFromChatId();
    }

    public Integer getForwardFromMessageId() {
        return message.getForwardFromMessageId();
    }

    public boolean isGroupMessage() {
        return message.isGroupMessage();
    }

    public boolean isUserMessage() {
        return message.isUserMessage();
    }

    public boolean isChannelMessage() {
        return message.isChannelMessage();
    }

    public boolean isSuperGroupMessage() {
        return message.isSuperGroupMessage();
    }

    public Long getChatId() {
        return message.getChatId();
    }

    public boolean hasText() {
        return message.hasText();
    }

    public boolean isCommand() {
        return message.isCommand();
    }

    public boolean hasDocument() {
        return message.hasDocument();
    }

    public boolean isReply() {
        return message.isReply();
    }

    public boolean hasLocation() {
        return message.hasLocation();
    }

    public Chat getForwardFromChat() {
        return message.getForwardFromChat();
    }

    public Integer getEditDate() {
        return message.getEditDate();
    }

    public Game getGame() {
        return message.getGame();
    }

    public boolean hasEntities() {
        return message.hasEntities();
    }

    public boolean hasPhoto() {
        return message.hasPhoto();
    }

    @Override
    public String toString() {
        return "Message{" +
                "message=" + message +
                ", from=" + from +
                ", forwardFrom=" + forwardFrom +
                ", newChatMember=" + newChatMember +
                ", leftChatMember=" + leftChatMember +
                '}';
    }
}
