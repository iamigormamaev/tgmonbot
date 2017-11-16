package interfaces;

import models.Event;
import models.ChatWithCommand;
import models.Update;
import models.User;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface LocalStore {

    boolean isAlreadyRegisteredUser(User user);

    void userRegistration(User user, ChatWithCommand chat);

    void addEvents(List<Event> eventsList);

    void deleteEvent(Event event);

    List<Event> getEvents(boolean isActive, boolean isStarted, boolean isFinished);

    ChatWithCommand getChatById (Long id);

    boolean containsChatById(Long id);

    ChatWithCommand putToChats (Long id, ChatWithCommand chat);

    boolean isRegisteredUserName (String name);

    User getUserByName (String name);

    Update updateQueuePeek();

    Update updateQueuePool();

    boolean updateQueueAdd(Update update);

    List<Event> getEventsListFilterByAuthor(User author);

    ChatWithCommand getChatByUser (User user);
}
