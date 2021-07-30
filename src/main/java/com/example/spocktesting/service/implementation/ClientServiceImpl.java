package com.example.spocktesting.service.implementation;

import com.example.spocktesting.dto.request.client.ClientCreateDtoRequest;
import com.example.spocktesting.dto.request.client.ClientUpdateDtoRequest;
import static com.example.spocktesting.util.facade.ClientFacade.*;
import com.example.spocktesting.model.Client;
import com.example.spocktesting.model.Discount;
import com.example.spocktesting.repository.ClientRepository;
import com.example.spocktesting.service.domain.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final DiscountServiceImpl discountService;

    @Autowired
    public ClientServiceImpl(ClientRepository repository, DiscountServiceImpl discountService) {
        this.repository = repository;
        this.discountService = discountService;
    }

    @Override
    public List<Client> getAll() {
        return repository.findAll();
    }

    @Override
    public Client create(ClientCreateDtoRequest dto) {
        Client client = createDtoRequestToModel(dto);
        Discount discount = discountService.get(dto.getDiscountId());
        client.setDiscount(discount);
        return save(client);
    }

    @Override
    public Client update(ClientUpdateDtoRequest dto) {
        Client client = get(dto.getId());
        client.setDiscount(discountService.get(dto.getDiscountId()));
        client = updateDtoRequestToModel(client, dto);
        return save(client);
    }

    @Override
    public Client get(Long id) {
        return repository.getById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Client save(Client model) {
        return repository.save(model);
    }

}
