package io.github.fabiocintra.login.security.custom.authentication;

import io.github.fabiocintra.login.model.User;
import io.github.fabiocintra.login.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationCustomFilter extends OncePerRequestFilter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            authentication = getAuthenticationFromToken(request);
        }

        if(mapAuthentication(authentication)){
            String userName = authentication.getName();
            User user = userService.searchByUserName(userName);
            if(user != null){
                authentication = new CustomAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
        }

    private boolean mapAuthentication(Authentication authentication) {
        return authentication instanceof JwtAuthenticationToken && authentication != null;
    }

    private Authentication getAuthenticationFromToken(HttpServletRequest request) {
        String token = getTokenFromCookie(request);
        if(token == null){
            return null;
        }
        Jwt jwt = jwtDecoder.decode(token);

        String username = jwt.getSubject();
        User user = userService.searchByUserName(username);

        return new CustomAuthentication(user);
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        if(request.getCookies() == null){
            return null;
        }

        for(Cookie cookie : request.getCookies()){
            if("ACCESS_TOKEN".equals(cookie.getName())){
                return cookie.getValue();
            }
        }

        return null;
    }
}
