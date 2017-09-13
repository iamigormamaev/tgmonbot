import Exceptions.DateParseException;
import Exceptions.NotEnoughArgsToParseException;
import Exceptions.NotRegisteredUserException;
import models.Event;
import implementations.EventParserImpl;
import interfaces.EventParser;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class EventParserImplTest {
    Map<String, User> registeredUsersMoc;
    EventParser eventParser;
    User masha = new User();
    User igor = new User();
    User katya = new User();
    User author = new User();

    @Before
    public void init() {
        registeredUsersMoc = new HashMap<>();
        registeredUsersMoc.put("igor", igor);
        registeredUsersMoc.put("masha", masha);
        registeredUsersMoc.put("katya", katya);
        registeredUsersMoc.put("author", author);
        eventParser = new EventParserImpl();

    }
    //[Тестируемый метод]_[Сценарий]_[Ожидаемое поведение].

    @Test
    public void parse_takeUsualGoodString_returnNormalEvent() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("igor 11.09.2017 15:00 Купить колбасы", author));
    }

    @Test
    public void parse_takeUsualStringWithAName_returnNormalEvent() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("@igor 11.09.2017 15:00 Купить колбасы", author));
    }

    @Test
    public void parse_takeManySpaces_returnNormalEvent() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("    igor 11.09.2017 15:00 Купить колбасы   ", author));
    }

    @Test
    public void parse_takeSomeTabs_returnNormalEvent() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("    igor 11.09.2017 15:00 Купить\tколбасы   ", author));
    }

    @Test
    public void parse_takeSomeCR_returnNormalEvent() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("    igor 11.09.2017 15:00 Купить\nколбасы   ", author));
    }

    @Test(expected = NotRegisteredUserException.class)
    public void parse_takeNonExistingUser_throwNotRegistregUserException() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("smiigor 11.09.2017 15:00 Купить колбасы", author));
    }

    @Test(expected = DateParseException.class)
    public void parse_takeWrongDate_throwDateParseException() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("igor 11.09.201715:00 Купить колбасы", author));
    }

    @Test(expected = NotEnoughArgsToParseException.class)
    public void parse_takeNoMessage_throwNotEnoughArgsToParseException() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("igor 11.09.2017 15:00", author));
    }

    @Test(expected = NotRegisteredUserException.class)
    public void parse_takeNoUser_throwNotRegistregUserException() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("11.09.2017 15:00 Купить колбасы", author));
    }

    @Test(expected = NotEnoughArgsToParseException.class)
    public void parse_takeNoDate_throwNotEnoughArgsToParseException() throws Exception {
        assertEquals(new Event(author, igor, new Date(1505131200000L), "Купить колбасы"),
                eventParser.parse("igor Купить колбасы", author));
    }


}