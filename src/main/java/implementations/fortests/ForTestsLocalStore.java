package implementations.fortests;


import implementations.*;
import interfaces.LocalStore;
import interfaces.Strings;
import models.Event;
import models.ChatWithCommand;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.util.*;

public class ForTestsLocalStore implements LocalStore {

    private List<Event> eventsList;
    private Map<String, User> registeredUsers;
    private Map<Long, ChatWithCommand> chats;
    private Map<User, ChatWithCommand> usersToChat;

    public ForTestsLocalStore() {
        eventsList = new ArrayList<>();
        registeredUsers = new TreeMap<>();
        chats = new HashMap<>();
        usersToChat = new HashMap<>();
    }



    public void userRegistration(Update update) {
        if (!registeredUsers.containsKey(update.getMessage().getFrom().getUserName())) {
            registeredUsers.put(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom());
            Main.getTcsMonitoringBot().sendMessage(update.getMessage().getChatId(), Strings.SUCCESSFUL_REGISTRATION);
            System.out.println(registeredUsers);
            usersToChat.put(update.getMessage().getFrom(), new ChatWithCommand(update.getMessage().getChat()));
        } else {
            Main.getTcsMonitoringBot().sendMessage(update.getMessage().getChatId(), Strings.ALREADY_REGISTERED);
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

    public Map<Long, ChatWithCommand> getChats() {
        return chats;
    }

    public Map<User, ChatWithCommand> getUsersToChat() {
        return usersToChat;
    }
}
