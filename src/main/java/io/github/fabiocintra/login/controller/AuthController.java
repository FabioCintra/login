package io.github.fabiocintra.login.controller;

import io.github.fabiocintra.login.model.dto.callback.CallbackRequest;
import io.github.fabiocintra.login.model.dto.token.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final String clientId =  System.getenv("CId");
    private final String clientSecret = System.getenv("CSecret");

    @GetMapping
    public void startAuth(HttpServletResponse response) throws IOException {
        String auhtorizeUrl = "http://localhost:8080/oauth2/authorize?"
                + "response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=http://localhost:5173/authorized";

        response.sendRedirect(auhtorizeUrl);
    }

    @PostMapping("/callback")
    public ResponseEntity rewardToken(@RequestBody CallbackRequest callbackRequest, HttpServletResponse response) throws IOException {
        //Criando o HashCode
        String hashCode = Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes());
        RestClient restClient = RestClient.create();

        String acessToken = restClient.post()
                .uri("http://localhost:8080/oauth2/token")
                .header("Authorization", "Basic " + hashCode)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(
                        "grant_type=authorization_code"
                                + "&code="+callbackRequest.code()
                                + "&redirect_uri=http://localhost:5173/authorized"
                )
                .retrieve()
                .body(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        TokenResponse tokenResponse = objectMapper.readValue(acessToken, TokenResponse.class);

        Cookie cookieAccess = new Cookie("ACCESS_TOKEN", tokenResponse.accessToken());
        cookieAccess.setHttpOnly(true);
        cookieAccess.setSecure(false);
        cookieAccess.setPath("/");
        cookieAccess.setMaxAge(3600);

        Cookie cookieRefresh = new Cookie("REFRESH_TOKEN", tokenResponse.refreshToken());
        cookieRefresh.setHttpOnly(true);
        cookieRefresh.setSecure(false);
        cookieRefresh.setPath("/");
        cookieRefresh.setMaxAge(60*90);

        response.addCookie(cookieAccess);
        response.addCookie(cookieRefresh);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity me(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return  ResponseEntity.ok().build();
    }
}
