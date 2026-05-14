package io.github.fabiocintra.login.controller;

import io.github.fabiocintra.login.model.Client;
import io.github.fabiocintra.login.model.dto.callback.CallbackRequest;
import io.github.fabiocintra.login.model.dto.client.ClientRequest;
import io.github.fabiocintra.login.model.dto.token.TokenResponse;
import io.github.fabiocintra.login.model.mappers.ClientMapper;
import io.github.fabiocintra.login.service.ClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;

@RestController
//@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public void createClient(@RequestBody @Valid ClientRequest request) {
        Client client = clientMapper.toEntity(request);
        clientService.save(client);
    }

    @GetMapping("/oauth2")
    public void startAuth(HttpServletResponse response) throws IOException {
        String auhtorizeUrl = "http://localhost:8080/oauth2/authorize?"
                + "response_type=code"
                + "&client_id=meu-client-id"
                + "&redirect_uri=http://localhost:5173/authorized";

        response.sendRedirect(auhtorizeUrl);
    }

    @PostMapping("/oauth2/callback")
    @ResponseStatus(HttpStatus.OK)
    public void rewardToken(@RequestBody CallbackRequest callbackRequest, HttpServletResponse response) throws IOException {
        //Criando o HashCode
        String hashCode = Base64.getEncoder()
                .encodeToString(("meu-client-id:meu-client-secret").getBytes());
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

        Cookie cookieAccess = new Cookie("ACCESS_TOKEN", tokenResponse.access_token());
        cookieAccess.setHttpOnly(true);
        cookieAccess.setSecure(false);
        cookieAccess.setPath("/");
        cookieAccess.setMaxAge(3600);

        Cookie cookieRefresh = new Cookie("ACCESS_TOKEN", tokenResponse.refresh_token());
        cookieRefresh.setHttpOnly(true);
        cookieRefresh.setSecure(false);
        cookieRefresh.setPath("/");
        cookieRefresh.setMaxAge(60*90);

        response.addCookie(cookieAccess);
        response.addCookie(cookieRefresh);
    }

}
