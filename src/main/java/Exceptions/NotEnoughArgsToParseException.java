package Exceptions;


public class NotEnoughArgsToParseException extends Exception {

    public NotEnoughArgsToParseException() {
        super("Not enough args to create event message");
    }
}
