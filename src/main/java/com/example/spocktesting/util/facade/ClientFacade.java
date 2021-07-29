package com.example.spocktesting.util.facade;

import com.example.spocktesting.dto.request.client.ClientCreateDtoRequest;
import com.example.spocktesting.dto.request.client.ClientUpdateDtoRequest;
import com.example.spocktesting.dto.response.client.ClientDtoResponse;
import com.example.spocktesting.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientFacade {

    public static ClientDtoResponse modelToDtoResponse(Client model) {
        ClientDtoResponse dto = new ClientDtoResponse();
        if (model.getId() != null)
            dto.setId(model.getId());
        if (model.getName() != null)
            dto.setName(model.getName());
        if (model.getSurname() != null)
            dto.setSurname(model.getSurname());
        if (model.getPhoneNumber() != null)
            dto.setPhoneNumber(model.getPhoneNumber());
        if (model.getDiscount() != null)
            dto.setDiscount(DiscountFacade.modelToDtoResponse(model.getDiscount()));
        return dto;
    }

    public static List<ClientDtoResponse> modelListToDtoResponseList(List<Client> modelList) {
        List<ClientDtoResponse> dtoList = new ArrayList<>();
        for (Client model : modelList)
            dtoList.add(modelToDtoResponse(model));
        return dtoList;
    }

    public static Client createDtoRequestToModel(ClientCreateDtoRequest dto) {
        Client model = new Client();
        if (dto.getName() != null)
            model.setName(dto.getName());
        if (dto.getSurname() != null)
            model.setSurname(dto.getSurname());
        if (dto.getPhoneNumber() != null)
            model.setPhoneNumber(dto.getPhoneNumber());
        return model;
    }

    public static Client updateDtoRequestToModel(Client model, ClientUpdateDtoRequest dto) {
        if (dto.getName() != null)
            model.setName(dto.getName());
        if (dto.getSurname() != null)
            model.setSurname(dto.getSurname());
        if (dto.getPhoneNumber() != null)
            model.setPhoneNumber(dto.getPhoneNumber());
        return model;
    }

}
