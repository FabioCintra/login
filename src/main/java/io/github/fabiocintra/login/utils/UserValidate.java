package io.github.fabiocintra.login.utils;

import io.github.fabiocintra.login.model.User;
import io.github.fabiocintra.login.repository.UserRepository;
import io.github.fabiocintra.login.utils.annotations.Validate;
import io.github.fabiocintra.login.utils.exceptions.UserNameExistsException;
import lombok.RequiredArgsConstructor;

@Validate
@RequiredArgsConstructor
public class UserValidate {

    private final UserRepository userRepository;

    public void userIsValid(User user){

        String username = user.getUsername();
        //checa se aquele userName ja esta cadastrado na base de dados
        if(existsByUserName(username)){
            throw new UserNameExistsException("Username already exists!");
        }
    }

    private Boolean existsByUserName(String userName){
        return userRepository.existsByUsername(userName);
    }

}
