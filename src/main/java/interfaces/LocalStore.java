package interfaces;

import models.*;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public interface LocalStore {

    boolean isAlreadyRegisteredUser(User user);

    void userRegistration(User user, ChatWithCommand chat);

    void addEvents(List<Event> eventsList);

    void finishEvent(Event event);

    void startEvent(Event event);

    List<Event> getEvents(EventStatus... statuses);

    ChatWithCommand getChatById(Long id);

    boolean containsChatById(Long id);

    ChatWithCommand addOrUpdateChat(Long id, ChatWithCommand chat);

    boolean isRegisteredUserName(String name);

    User getUserByName(String name);

    Update updateQueuePeek();

    Update updateQueuePool();

    boolean updateQueueAdd(Update update);

    List<Event> getEventsListFilterByAuthor(User author);

    ChatWithCommand getChatByUser(User user);

    void setChatPreviousCommand(ChatWithCommand chat, Command command);

    void initStore();
}
