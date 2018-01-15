package Exceptions;

public class DbProblemException extends Exception {
    public DbProblemException() {
    }

    public DbProblemException(String message) {
        super(message);
    }

    public DbProblemException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbProblemException(Throwable cause) {
        super(cause);
    }

    public DbProblemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
