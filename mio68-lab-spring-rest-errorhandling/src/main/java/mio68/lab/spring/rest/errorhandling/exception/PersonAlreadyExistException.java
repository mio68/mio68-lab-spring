package mio68.lab.spring.rest.errorhandling.exception;

/**
 * This is an application exception.
 */
public class PersonAlreadyExistException extends Exception{

    public PersonAlreadyExistException(String message) {
        super(message);
    }
}
