package io.github.fabiocintra.login.utils.exceptions;

public class UserNameExistsException extends RuntimeException{

    public UserNameExistsException(String message){
        super(message);
    }

}
