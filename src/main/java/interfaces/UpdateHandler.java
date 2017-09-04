package interfaces;


import models.ChatWithCommand;
import org.telegram.telegrambots.api.objects.Update;

public interface UpdateHandler {
    void handleUpdate(Update update, ChatWithCommand chat);


}
