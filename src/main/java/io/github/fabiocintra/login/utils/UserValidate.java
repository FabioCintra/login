package io.github.fabiocintra.login.resources.utils;

import io.github.fabiocintra.login.resources.model.User;
import io.github.fabiocintra.login.resources.repository.UserRepository;
import io.github.fabiocintra.login.resources.utils.annotations.Validate;
import io.github.fabiocintra.login.resources.utils.exceptions.UserNameExistsException;
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
