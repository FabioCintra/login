package io.github.fabiocintra.login.resources.model.mappers;

import io.github.fabiocintra.login.resources.model.User;
import io.github.fabiocintra.login.resources.model.dto.user.UserRequest;
import io.github.fabiocintra.login.resources.model.dto.user.UserResponse;
import io.github.fabiocintra.login.resources.utils.annotations.Mapper;

@Mapper
public class UserMapper {

    public User toEntity(UserRequest request){
        User user = new User();

        user.setUsername(request.userName());
        user.setPassword(request.password());
        user.setDateOfBirth(request.dateOfBirth());

        return user;
    }

    public UserResponse toResponse(User user){

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getDateOfBirth(),
                user.getCreateAccount()
        );

    }

}
