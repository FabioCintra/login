package io.github.fabiocintra.login.controller;

import io.github.fabiocintra.login.utils.exceptions.ClientIdExistsException;
import io.github.fabiocintra.login.utils.exceptions.UserNameExistsException;
import io.github.fabiocintra.login.utils.exceptions.UserNotFoundException;
import io.github.fabiocintra.login.utils.exceptions.tratament.FieldError;
import io.github.fabiocintra.login.utils.exceptions.tratament.TranslaterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TranslaterException methodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> errorList = e.getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .toList();

        TranslaterException exception = new TranslaterException(HttpStatus.BAD_REQUEST.value(), "Insercao incorreta de dados!", errorList);

        return exception;
    }

    @ExceptionHandler(UserNameExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public TranslaterException userNameExistsException(UserNameExistsException e){
        return new TranslaterException(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TranslaterException userNotFoundException(UserNotFoundException e){
        return new TranslaterException(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(ClientIdExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public TranslaterException clientIdExistsException(ClientIdExistsException e){
        return new TranslaterException(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                List.of()
        );
    }

}
