package interfaces;

import Exceptions.DateFromPastException;
import Exceptions.DateParseException;
import Exceptions.NotEnoughArgsToParseException;
import Exceptions.NotRegisteredUserException;
import models.Event;
import models.User;

public interface EventParser  {
    Event parse(String inString, User author) throws NotEnoughArgsToParseException, NotRegisteredUserException, DateParseException, DateFromPastException;
}
