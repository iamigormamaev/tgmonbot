package interfaces;

import Exceptions.DateParseException;
import Exceptions.NotEnoughArgsToParseException;
import Exceptions.NotRegistregUserException;
import implementations.Event;
import org.telegram.telegrambots.api.objects.User;

public interface EventParser  {
    Event parse(String inString, User author) throws NotEnoughArgsToParseException, NotRegistregUserException, DateParseException;
}
