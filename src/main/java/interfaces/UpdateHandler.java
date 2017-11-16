package interfaces;


import models.ChatWithCommand;
import models.Update;

public interface UpdateHandler {
    void handleUpdate(Update update, ChatWithCommand chat);

    void pollingUpdates();


}
