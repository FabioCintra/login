package io.github.fabiocintra.login.service;

import io.github.fabiocintra.login.model.Client;
import io.github.fabiocintra.login.repository.ClientRepository;
import io.github.fabiocintra.login.utils.ClientValidate;
import io.github.fabiocintra.login.utils.exceptions.ClientIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientValidate clientValidate;
    private final PasswordEncoder passwordEncoder;

    public void save(Client client) {
        clientValidate.clientIsValid(client);
        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        clientRepository.save(client);
    }

    public Client findByClientId(String clientId) {
        Optional<Client> clientOptional = clientRepository.findByClientId(clientId);

        if(clientOptional.isEmpty()){
            throw new ClientIdNotFoundException("Client Id not found!");
        }

        return clientOptional.get();
    }

}
