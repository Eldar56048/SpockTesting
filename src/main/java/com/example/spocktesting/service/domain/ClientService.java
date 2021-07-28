package com.example.spocktesting.service.domain;

import com.example.spocktesting.dto.request.client.ClientCreateDtoRequest;
import com.example.spocktesting.dto.request.client.ClientUpdateDtoRequest;
import com.example.spocktesting.model.Client;
import com.example.spocktesting.service.BaseService;

public interface ClientService extends BaseService<Client> {
    Client create(ClientCreateDtoRequest dto);
    Client update(ClientUpdateDtoRequest dto);
}
