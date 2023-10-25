package ru.netology.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class, InvalidCredentialsException.class, IOException.class})
    ErrorResponse handleBindException(Exception e) {
        logger.error(e.getMessage());
        return new ErrorResponse( -1, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    ErrorResponse handleAuthorizationException(AuthException e) {
        logger.error(e.getMessage());
        return new ErrorResponse( -1, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    ErrorResponse handleRuntimeException(RuntimeException e) {
        logger.error(e.getMessage());
        return new ErrorResponse( -1, e.getMessage());
    }
}
