package mio68.lab.spring.rest.errorhandling.handler;

import lombok.Builder;
import lombok.Data;
import mio68.lab.spring.rest.errorhandling.exception.PersonAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class Handler {

    @ExceptionHandler(PersonAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponceDto handlePersonAlreadyExistException(
            PersonAlreadyExistException ex,
            HttpServletRequest request) {

        return ExceptionResponceDto.builder()
                .exceptionClassName(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponceDto handleRuntimeException(
            RuntimeException ex,
            HttpServletRequest request) {

        return ExceptionResponceDto.builder()
                .exceptionClassName(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }


    @Builder
    @Data
    public static class ExceptionResponceDto {
        private final String exceptionClassName;
        private final String message;
        private final String path;
    }

}

