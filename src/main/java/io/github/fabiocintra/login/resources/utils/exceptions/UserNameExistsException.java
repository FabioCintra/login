package io.github.fabiocintra.login.resources.utils.exceptions;

public class UserNameExistsException extends RuntimeException{

    public UserNameExistsException(String message){
        super(message);
    }

}
