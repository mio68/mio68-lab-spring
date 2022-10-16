package mio68.lab.spring.webapp.errorhandlingapp.handler;

import mio68.lab.spring.webapp.errorhandlingapp.exeception.PersonAlreadyExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonAlreadyExistException.class)
    public ResponseEntity<Object> handlePersonAlreadyExistException(
            PersonAlreadyExistException ex,
            WebRequest request) {

        return handleExceptionInternal(
                ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

//    @ExceptionHandler(value = {
//            IllegalArgumentException.class,
//            IllegalStateException.class})
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex,
            WebRequest request) {

        return handleExceptionInternal(
                ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }


//    @Builder
//    public static class ExceptionDto {
//        private final String exception;
//        private final String message;
//    }
}

