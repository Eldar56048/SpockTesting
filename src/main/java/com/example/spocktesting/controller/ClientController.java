package com.example.spocktesting.controller;

import com.example.spocktesting.dto.request.client.ClientCreateDtoRequest;
import com.example.spocktesting.dto.request.client.ClientUpdateDtoRequest;
import com.example.spocktesting.exception.domain.DtoException;
import com.example.spocktesting.exception.domain.ResourceNotFoundException;
import com.example.spocktesting.service.implementation.ClientServiceImpl;
import com.example.spocktesting.service.implementation.DiscountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static com.example.spocktesting.util.facade.ClientFacade.*;
import static com.example.spocktesting.constant.ResponseCode.*;
import static com.example.spocktesting.constant.ResponseCodeMessage.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientServiceImpl clientService;
    private final DiscountServiceImpl discountService;

    @Autowired
    public ClientController(ClientServiceImpl clientService, DiscountServiceImpl discountService) {
        this.clientService = clientService;
        this.discountService = discountService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(modelListToDtoResponseList(clientService.getAll()));
    }

    @PutMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClientCreateDtoRequest dto) {
        if (!discountService.existsById(dto.getDiscountId()))
            throw new ResourceNotFoundException(DISCOUNT_NOT_FOUND_MESSAGE(dto.getDiscountId()), DISCOUNT_NOT_FOUND);
        return ResponseEntity.ok(modelToDtoResponse(clientService.create(dto)));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> get(@PathVariable Long clientId) {
        if (!clientService.existsById(clientId))
            throw new ResourceNotFoundException(CLIENT_NOT_FOUND_MESSAGE(clientId), CLIENT_NOT_FOUND);
        return ResponseEntity.ok(modelToDtoResponse(clientService.get(clientId)));
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<?> update(@PathVariable Long clientId, @Valid @RequestBody ClientUpdateDtoRequest dto) {
        if (dto.getId()!=clientId)
            throw new DtoException(CLIENT_TWO_ANOTHER_ID_MESSAGE, CLIENT_TWO_ANOTHER_ID);
        if (!clientService.existsById(clientId))
            throw new ResourceNotFoundException(CLIENT_NOT_FOUND_MESSAGE(clientId), CLIENT_NOT_FOUND);
        if (!discountService.existsById(dto.getDiscountId()))
            throw new ResourceNotFoundException(DISCOUNT_NOT_FOUND_MESSAGE(dto.getDiscountId()), DISCOUNT_NOT_FOUND);
        return ResponseEntity.ok(modelToDtoResponse(clientService.update(dto)));
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> delete(@PathVariable Long clientId) {
        if (!clientService.existsById(clientId))
            throw new ResourceNotFoundException(CLIENT_NOT_FOUND_MESSAGE(clientId), CLIENT_NOT_FOUND);
        clientService.delete(clientId);
        return ResponseEntity.ok(CLIENT_SUCCESSFULLY_DELETED);
    }
}
