package Exceptions;

/**
 * Created by Игорь on 28.02.2017.
 */
public class DateParseException extends Exception {

    public DateParseException() {
        super("Can't parse date");
    }
}
