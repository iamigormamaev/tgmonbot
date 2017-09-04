package implementations;

import Exceptions.DateParseException;
import Exceptions.NotEnoughArgsToParseException;
import Exceptions.NotRegisteredUserException;

import interfaces.EventParser;
import models.Event;
import org.telegram.telegrambots.api.objects.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;


public class EventParserImpl implements EventParser {
    private final static Logger LOGGER = Logger.getLogger(EventParserImpl.class.getName());
    private Map<String, User> registeredUsers;

    public EventParserImpl(Map<String, User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public Event parse(String inString, User author) throws NotEnoughArgsToParseException, NotRegisteredUserException, DateParseException {
        try {
            LOGGER.info("parsing string: \"" + inString + "\" author: " + author);
            inString = inString.trim();
            String[] words = inString.split("\\s");
            String userName = words[0];
            String dateTimeString = words[1] + " " + words[2];
            StringBuilder message = new StringBuilder("");
            if (words.length > 3) {
                for (int i = 3; i < words.length; i++) {
                    message.append(words[i] + " ");
                }
            } else throw new ArrayIndexOutOfBoundsException();

            if (userName.charAt(0) == '@') {
                userName = userName.substring(1);
            }
            if (!registeredUsers.containsKey(userName)) {
                LOGGER.warning("NotRegisteredUserException was throw for string: \""+ inString + "\"");
                throw new NotRegisteredUserException();
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
            Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            Event resultEvent = new Event(author, registeredUsers.get(userName), date, message.toString().trim());

            return resultEvent;

        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.warning("NotEnoughArgsToParseException was throw for string: \""+ inString + "\"");
            throw new NotEnoughArgsToParseException();
        } catch (DateTimeParseException e) {
            LOGGER.warning("DateParseException was throw for string: \""+ inString + "\"");
            throw new DateParseException();
        }


    }


}
