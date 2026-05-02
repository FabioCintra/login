package io.github.fabiocintra.login.resources.service;

import io.github.fabiocintra.login.resources.entity.User;
import io.github.fabiocintra.login.resources.repository.UserRepository;
import io.github.fabiocintra.login.resources.utils.UserValidate;
import io.github.fabiocintra.login.resources.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidate userValidate;

    public User create(User user){
        userValidate.userIsValid(user);
        return userRepository.save(user);
    }

    public User searchById(Integer id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found!");
        }
        return userOptional.get();
    }

    public User searchByUserName(String userName){
        return userRepository.findByUsername(userName);
    }

}
