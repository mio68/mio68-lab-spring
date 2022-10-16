package mio68.lab.spring.webapp.errorhandlingapp.exeception;

/**
 * This is application exception.
 */
public class PersonAlreadyExistException extends Exception{

    public PersonAlreadyExistException(String message) {
        super(message);
    }
}
