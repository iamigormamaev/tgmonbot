package Exceptions;


public class ToManyArgsException extends Exception {

    public ToManyArgsException() {
        super("To many args in creating event message");
    }
}
