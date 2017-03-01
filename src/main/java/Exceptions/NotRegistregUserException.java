package Exceptions;

public class NotRegistregUserException extends Exception{

    public NotRegistregUserException() {
        super("User is not registred");
    }
}
