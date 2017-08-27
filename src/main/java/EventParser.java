import org.telegram.telegrambots.api.objects.Update;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EventParser {
    private static EventParser instance;

    private EventParser() {
    }

    public static EventParser getInstance() {
        if (instance == null) {
            instance = new EventParser();
            return instance;
        } else return instance;
    }

    public List<Event> parse(Update update) {
        String text = update.getMessage().getText();
        List<Event> result = new ArrayList<>();

        String[] eventsStrings = text.split("\n");
        for (String s :
                eventsStrings) {
            String[] argsStrings = s.split(" ");
            if (argsStrings.length != 2) {
                Main.getMyMonitoringBot().sendMessage(update.getMessage().getChatId(), Strings.WRONG_EVENT + s);
            } else {
                if (argsStrings[0].charAt(0) == '@') {
                    argsStrings[0] = argsStrings[0].substring(1);
                }
                if (!LocalStore.getInstance().getRegisteredUsers().containsKey(argsStrings[0])) {
                    Main.getMyMonitoringBot().sendMessage(update.getMessage().getChatId(), Strings.NOT_REGISTERED_USER + argsStrings[0]);
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        Date date = simpleDateFormat.parse(argsStrings[1]);
                        date.setHours(Config.EVENT_HOURS);
                        date.setMinutes(Config.EVENT_MINUTES);
                        date.setSeconds(Config.EVENT_SECONDS);
                        result.add(new Event(update.getMessage().getFrom(),LocalStore.getInstance().getRegisteredUsers().get(argsStrings[0]), date));

                    } catch (ParseException e) {
                        Main.getMyMonitoringBot().sendMessage(update.getMessage().getChatId(), Strings.CANT_PARSE_DATE + argsStrings[1]);
                        continue;
                    }
                }
            }
        }

        return result;
    }


}
