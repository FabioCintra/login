package io.github.fabiocintra.login.controller;

import io.github.fabiocintra.login.model.Client;
import io.github.fabiocintra.login.model.dto.client.ClientRequest;
import io.github.fabiocintra.login.model.mappers.ClientMapper;
import io.github.fabiocintra.login.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createClient(@RequestBody @Valid ClientRequest request) {
        Client client = clientMapper.toEntity(request);
        clientService.save(client);
    }

}
