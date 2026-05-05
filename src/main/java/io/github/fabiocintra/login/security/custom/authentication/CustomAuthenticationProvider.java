package io.github.fabiocintra.login.security.custom.authentication;

import io.github.fabiocintra.login.model.User;
import io.github.fabiocintra.login.service.UserService;
import io.github.fabiocintra.login.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        User userFinded = userService.searchByUserName(login);
        if(userFinded == null){
            throw new UserNotFoundException("User/Password not exists");
        }

        if(!passwordEncoder.matches(password, userFinded.getPassword())){
            throw new UserNotFoundException("User/Password not exists");
        }

        return new CustomAuthentication(userFinded);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
