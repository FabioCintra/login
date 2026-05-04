package io.github.fabiocintra.login.resources.service;

import io.github.fabiocintra.login.resources.model.User;
import io.github.fabiocintra.login.resources.repository.UserRepository;
import io.github.fabiocintra.login.resources.utils.UserValidate;
import io.github.fabiocintra.login.resources.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidate userValidate;
    private final PasswordEncoder passwordEncoder;

    public User create(User user){
        userValidate.userIsValid(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
