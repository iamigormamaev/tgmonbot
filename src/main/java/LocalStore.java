import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LocalStore {
    private static LocalStore instance = new LocalStore();
    private static List<Event> eventsList;
    private static Map<Integer, User> registeredUsers;

    private LocalStore() {
        eventsList = new ArrayList<>();
        registeredUsers = new TreeMap<>();
    }

    public static LocalStore getInstance() {

        return instance;
    }

    public void userRegistration(Update update) {
        if (!registeredUsers.containsKey(update.getMessage().getFrom().getId())) {
            registeredUsers.put(update.getMessage().getFrom().getId(), update.getMessage().getFrom());
            Main.getMyMonitoringBot().sendMessage(update.getMessage().getChatId(), Strings.successfulRegistration);
            System.out.println(registeredUsers);
        } else {
            Main.getMyMonitoringBot().sendMessage(update.getMessage().getChatId(), Strings.alreadyRegistered);
        }
    }


}
