package models;

import org.telegram.telegrambots.api.objects.CallbackQuery;

import org.telegram.telegrambots.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;

public class Update {
    private org.telegram.telegrambots.api.objects.Update update;
    private Message message;
    private Message editedMessage;
    private Message channelPost;
    private Message editedChannelPost;


    public Update(org.telegram.telegrambots.api.objects.Update update) {
        this.update = update;
        message = update.getMessage() != null ? new Message(update.getMessage()) : null;
        editedMessage = update.getEditedMessage() != null ? new Message(update.getEditedMessage()) : null;
        channelPost = update.getChannelPost() != null ? new Message(update.getChannelPost()) : null;
        editedChannelPost = update.getEditedChannelPost() != null ? new Message(update.getEditedChannelPost()) : null;
    }

    public Integer getUpdateId() {
        return update.getUpdateId();
    }

    public Message getMessage() {
        return message;
    }

    public InlineQuery getInlineQuery() {
        return update.getInlineQuery();
    }

    public ChosenInlineQuery getChosenInlineQuery() {
        return update.getChosenInlineQuery();
    }

    public CallbackQuery getCallbackQuery() {
        return update.getCallbackQuery();
    }

    public Message getEditedMessage() {
        return editedMessage;
    }

    public Message getChannelPost() {
        return channelPost;
    }

    public Message getEditedChannelPost() {
        return editedChannelPost;
    }

    public boolean hasMessage() {
        return update.hasMessage();
    }

    public boolean hasInlineQuery() {
        return update.hasInlineQuery();
    }

    public boolean hasChosenInlineQuery() {
        return update.hasChosenInlineQuery();
    }

    public boolean hasCallbackQuery() {
        return update.hasCallbackQuery();
    }

    public boolean hasEditedMessage() {
        return update.hasEditedMessage();
    }

    public boolean hasChannelPost() {
        return update.hasChannelPost();
    }

    public boolean hasEditedChannelPost() {
        return update.hasEditedChannelPost();
    }
}
