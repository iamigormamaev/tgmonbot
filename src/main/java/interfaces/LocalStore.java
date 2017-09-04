package interfaces;

import models.Event;
import models.ChatWithCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface LocalStore {

    void userRegistration(Update update);

    void addEvents(List<Event> eventsList);

    void deleteEvent(Event event);

    List<Event> getEventsList();

    Map<String, User> getRegisteredUsers();

    Map<Long, ChatWithCommand> getChats();

    Map<User, ChatWithCommand> getUsersToChat();

    Queue<Update> getUpdateQueue();
}
