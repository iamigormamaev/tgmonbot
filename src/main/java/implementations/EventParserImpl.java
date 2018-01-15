package implementations;

import Exceptions.DateFromPastException;
import Exceptions.DateParseException;
import Exceptions.NotEnoughArgsToParseException;
import Exceptions.NotRegisteredUserException;
import factories.LocalStoreFactory;
import interfaces.EventParser;
import interfaces.LocalStore;
import models.Event;
import models.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.logging.Logger;


public class EventParserImpl implements EventParser {
    private final static Logger LOGGER = Logger.getLogger(EventParserImpl.class.getName());

    LocalStore localStore = new LocalStoreFactory().getDefaultLocalStore();

    public EventParserImpl() {

    }

    public Event parse(String inString, User author) throws NotEnoughArgsToParseException, NotRegisteredUserException, DateParseException, DateFromPastException {
        try {
//          LOGGER.info("parsing string: \"" + inString + "\" author: " + author);
            inString = inString.trim();
            String[] words = inString.split("\\s");
            String userName = words[0].toLowerCase();
            String dateTimeString = words[1] + " " + words[2];
            StringBuilder message = new StringBuilder("");
            if (userName.charAt(0) == '@') {
                userName = userName.substring(1);
            }
            if (!localStore.isRegisteredUserName(userName)) {
                dateTimeString = words[0] + " " + words[1];
                userName = author.getUserName();
                for (int i = 2; i < words.length; i++) {
                    message.append(words[i] + " ");
                }
            } else {
                for (int i = 3; i < words.length; i++) {
                    message.append(words[i] + " ");
                }
            }
            String[] formatters = {"dd.MM.yyyy HH:mm", "dd.MM.yyyy H:mm", "dd.MM.yyyy HH:m", "dd.MM.yyyy H:m"};
            LocalDateTime dateTime = null;
            for (int i = 0; i < formatters.length; i++) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatters[i]);
                    dateTime = LocalDateTime.parse(dateTimeString, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    if (i == formatters.length - 1) throw e;
                }
            }

            Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            if (date.before(new Date(System.currentTimeMillis()))) {
                LOGGER.warning("DateFromPastException was throw for string: \"" + inString + "\"");
                throw new DateFromPastException();
            }
            Event resultEvent = new Event(author, localStore.getUserByName(userName), date, message.toString().trim());
            LOGGER.info("success parsing: \"" + inString + "\" author: " + author + " to event: " + resultEvent);
            return resultEvent;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.warning("NotEnoughArgsToParseException was throw for string: \"" + inString + "\"");
            throw new NotEnoughArgsToParseException();
        } catch (DateTimeParseException e) {
            LOGGER.warning("DateParseException was throw for string: \"" + inString + "\"");
            throw new DateParseException();
        }
    }
}
