package Exceptions;

public class NotRegisteredUserException extends Exception{

    public NotRegisteredUserException() {
        super("User is not registred");
    }
}
