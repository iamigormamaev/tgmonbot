package implementations;

import interfaces.LocalStore;
import models.Event;
import models.ChatWithCommand;
import models.Update;
import models.User;

import java.util.*;
import java.util.logging.Logger;

public class CollectionsLocalStore implements LocalStore {
    private final static Logger LOGGER = Logger.getLogger(CollectionsLocalStore.class.getName());
    private static CollectionsLocalStore instance = new CollectionsLocalStore();
    private List<Event> eventsList;
    private Map<String, User> registeredUsersNames;
    private Map<Integer, User> registeredUsers;
    private Map<Long, ChatWithCommand> chats;
    private Map<User, ChatWithCommand> usersToChat;
    private Queue<Update> updateQueue;
    private MainTimerTask mainTimerTask;

    private CollectionsLocalStore() {
        eventsList = new ArrayList<>();
        registeredUsersNames = new TreeMap<>();
        registeredUsers = new TreeMap<>();
        chats = new HashMap<>();
        usersToChat = new HashMap<>();
        updateQueue = new ArrayDeque<>();
        mainTimerTask = new MainTimerTask();
    }

    public static CollectionsLocalStore getInstance() {
        return instance;
    }

    public void userRegistration(User user, ChatWithCommand chat) {
        registeredUsers.put(user.getId(), user);
        if (user.getUserName() != null) {
            registeredUsersNames.put(user.getUserName(), user);
        }
        usersToChat.put(user, chat);
        LOGGER.info("Registered user: " + user);
    }

    public boolean isAlreadyRegisteredUser(User user) {
        try {
            return registeredUsers.containsKey(user.getId()) && registeredUsersNames.containsKey(user.getUserName());
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void addEvents(List<Event> eventsList) {
        this.eventsList.addAll(eventsList);
        mainTimerTask.run();
        LOGGER.info("Added events: " + eventsList);
    }

    public void deleteEvent(Event event) {
        event.setActive(false);
        LOGGER.info("Delete event: " + event);
    }

    public List<Event> getEvents(boolean isActive, boolean isStarted, boolean isFinished) {
        List<Event> resultList = new ArrayList<>();
        for (Event e :
                eventsList) {
            if (e.isActive() == isActive && e.isStarted() == isStarted && e.isFinished() == isFinished) {
                resultList.add(e);
            }
        }
        return resultList;
    }

    public boolean isRegisteredUserName(String name) {
        return registeredUsersNames.containsKey(name);
    }

    @Override
    public User getUserByName(String name) {
        return registeredUsersNames.get(name);
    }

    @Override
    public ChatWithCommand getChatById(Long id) {
        return chats.get(id);
    }

    @Override
    public ChatWithCommand putToChats(Long id, ChatWithCommand chat) {
        LOGGER.info("New chat added to chats: " + chat);
        return chats.put(id, chat);
    }

    @Override
    public boolean containsChatById(Long id) {
        return chats.containsKey(id);
    }

    public ChatWithCommand getChatByUser(User user) {
        return usersToChat.get(user);
    }

    @Override
    public Update updateQueuePeek() {
        return updateQueue.peek();
    }

    @Override
    public Update updateQueuePool() {
        return updateQueue.poll();
    }

    @Override
    public boolean updateQueueAdd(Update update) {
        return updateQueue.add(update);
    }

    @Override
    public List<Event> getEventsListFilterByAuthor(User author) {
        List<Event> resultList = new ArrayList<>();
        for (Event e :
                eventsList) {
            if (e.isActive() && !e.isFinished() && e.getAuthor().getId()==author.getId()) {
                resultList.add(e);
            }
        }
        return resultList;
    }
}
