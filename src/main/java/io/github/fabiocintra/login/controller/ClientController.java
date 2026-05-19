package io.github.fabiocintra.login.controller;

import io.github.fabiocintra.login.model.Client;
import io.github.fabiocintra.login.model.dto.client.ClientRequest;
import io.github.fabiocintra.login.model.mappers.ClientMapper;
import io.github.fabiocintra.login.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}
