import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.util.*;

public class LocalStore {
    private static LocalStore instance = new LocalStore();
    private List<Event> eventsList;
    private Map<String, User> registeredUsers;
    private Map<Long, MyChat> chats;
    private Map<User, MyChat> usersToChat;

    private LocalStore() {
        eventsList = new ArrayList<>();
        registeredUsers = new TreeMap<>();
        chats = new HashMap<>();
        usersToChat = new HashMap<>();
    }

    public static LocalStore getInstance() {
        return instance;
    }

    public void userRegistration(Update update) {
        if (!registeredUsers.containsKey(update.getMessage().getFrom().getUserName())) {
            registeredUsers.put(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom());
            Main.getMyMonitoringBot().sendMessage(update.getMessage().getChatId(), Strings.SUCCESSFUL_REGISTRATION);
            System.out.println(registeredUsers);
            usersToChat.put(update.getMessage().getFrom(), new MyChat(update.getMessage().getChat()));
        } else {
            Main.getMyMonitoringBot().sendMessage(update.getMessage().getChatId(), Strings.ALREADY_REGISTERED);
        }
    }

    public void addEvents(List<Event> eventsList) {
        this.eventsList.addAll(eventsList);
        System.out.println(this.eventsList);
        new MainTimerTask().run();
    }

    public void deleteEvent(Event event) {
        this.eventsList.remove(event);
    }

    public List<Event> getEventsList() {
        return eventsList;
    }

    public Map<String, User> getRegisteredUsers() {
        return registeredUsers;
    }

    public Map<Long, MyChat> getChats() {
        return chats;
    }

    public Map<User, MyChat> getUsersToChat() {
        return usersToChat;
    }
}
