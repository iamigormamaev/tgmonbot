package interfaces;

import implementations.Event;
import implementations.MyChat;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.util.List;
import java.util.Map;

public interface LocalStore {

    void userRegistration(Update update);

    void addEvents(List<Event> eventsList);

    void deleteEvent(Event event);

    List<Event> getEventsList();

    Map<String, User> getRegisteredUsers();

    Map<Long, MyChat> getChats();

    Map<User, MyChat> getUsersToChat();
}
