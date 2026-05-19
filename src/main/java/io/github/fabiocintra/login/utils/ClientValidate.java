package io.github.fabiocintra.login.utils;

import io.github.fabiocintra.login.model.Client;
import io.github.fabiocintra.login.repository.ClientRepository;
import io.github.fabiocintra.login.utils.annotations.Validate;
import io.github.fabiocintra.login.utils.exceptions.ClientIdExistsException;
import lombok.RequiredArgsConstructor;

@Validate
@RequiredArgsConstructor
public class ClientValidate {

    private final ClientRepository clientRepository;

    public void clientIsValid(Client client) {
        if(clientIdExists(client.getClientId()))
            throw new ClientIdExistsException("Client with id '" + client.getClientId() + "' already exists");
    }

    private boolean clientIdExists(String clientId) {
        return clientRepository.existsByClientId(clientId);
    }

}
