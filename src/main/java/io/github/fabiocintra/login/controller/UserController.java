package io.github.fabiocintra.login.resources.controller;

import io.github.fabiocintra.login.resources.model.User;
import io.github.fabiocintra.login.resources.model.dto.user.UserRequest;
import io.github.fabiocintra.login.resources.model.dto.user.UserResponse;
import io.github.fabiocintra.login.resources.model.mappers.UserMapper;
import io.github.fabiocintra.login.resources.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UserRequest request){
        User user = userMapper.toEntity(request);
        userService.create(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse search(@PathVariable("id") Integer id){
        User user = userService.searchById(id);
        return userMapper.toResponse(user);
    }

}
