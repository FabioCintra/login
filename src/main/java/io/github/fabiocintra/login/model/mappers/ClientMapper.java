package io.github.fabiocintra.login.model.mappers;

import io.github.fabiocintra.login.model.Client;
import io.github.fabiocintra.login.model.dto.client.ClientRequest;
import io.github.fabiocintra.login.utils.annotations.Mapper;

@Mapper
public class ClientMapper {

    public Client toEntity(ClientRequest clientRequest) {
        Client client = new Client();
        client.setClientId(clientRequest.clientId());
        client.setClientSecret(clientRequest.clientSecret());
        client.setCallbackUrl(clientRequest.callbackUrl());
        return client;
    }

}
