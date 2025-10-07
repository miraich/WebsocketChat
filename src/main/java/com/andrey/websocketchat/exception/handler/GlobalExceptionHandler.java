package com.andrey.websocketchat.exception.handler;

import com.andrey.websocketchat.dto.error.ErrorRs;
import com.andrey.websocketchat.exception.EntityAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ErrorRs handleEntityNotFoundException(EntityAlreadyExistsException e) {
        return new ErrorRs(e.getMessage());
    }

}
