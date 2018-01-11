package implementations;

import interfaces.LocalStore;
import models.*;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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

    protected CollectionsLocalStore() {
        eventsList = new ArrayList<>();
        registeredUsersNames = new TreeMap<>();
        registeredUsers = new TreeMap<>();
        chats = new HashMap<>();
        usersToChat = new HashMap<>();
        updateQueue = new LinkedBlockingQueue<>();
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

    public void finishEvent(Event event) {
        event.setStatus(EventStatus.FINISHED);
        LOGGER.info("Delete event: " + event);
    }

    @Override
    public void startEvent(Event event) {
        event.setStatus(EventStatus.STARTED);
        LOGGER.info("Start event: " + event);
    }

    public List<Event> getEvents(EventStatus... statuses) {
        List<Event> resultList = new ArrayList<>();
        for (Event e :
                eventsList) {
            if (Arrays.asList(statuses).contains(e.getStatus())) {
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
    public ChatWithCommand addOrUpdateChat(Long id, ChatWithCommand chat) {
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
            if (e.getStatus() != EventStatus.FINISHED && e.getAuthor().getId() == author.getId()) {
                resultList.add(e);
            }
        }
        return resultList;
    }

    @Override
    public void setChatPreviousCommand(ChatWithCommand chat, Command command) {
        chat.setPreviousCommand(command);
    }

    @Override
    public void initStore() {

    }
}
