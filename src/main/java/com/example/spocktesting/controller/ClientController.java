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

// Look up what brings you @RestController annotation. You can make your controllers cleaner without having to create ResponseEntity manually in every method
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    // It's not flexible to autowire dependencies by its implementations. Always use interface. Then you could introduce another implementation
    // to the project easily
    private final ClientServiceImpl clientService;
    private final DiscountServiceImpl discountService;

    @Autowired
    public ClientController(ClientServiceImpl clientService, DiscountServiceImpl discountService) {
        this.clientService = clientService;
        this.discountService = discountService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        // Avoid using statics as much as possible. Use them when you really can't do anything other.
        // Statics make every component hard to test in case of complex logic
        return ResponseEntity.ok(modelListToDtoResponseList(clientService.getAll()));
    }

    // Learn the semantics of HTTP methods. https://www.researchgate.net/figure/Summary-of-HTTP-methods-and-description-of-its-actions_tbl2_330105570
    @PutMapping
    // @RequestBody can be also left out when using @RestController
    public ResponseEntity<?> create(@Valid @RequestBody ClientCreateDtoRequest dto) {
        // Write your controllers as clean as possible. Leave exception throwing and validation logic to service layer.
        // Controllers should only map request/response objects and move execution to service layer fast.
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

    // Not quite the right HTTB verb here as well
    @PostMapping("/{clientId}")
    public ResponseEntity<?> update(@PathVariable Long clientId, @Valid @RequestBody ClientUpdateDtoRequest dto) {
        // Again. Too many validations for a controller
        if (!dto.getId().equals(clientId))
            throw new DtoException(CLIENT_TWO_ANOTHER_ID_MESSAGE, CLIENT_TWO_ANOTHER_ID);
        if (!clientService.existsById(clientId))
            throw new ResourceNotFoundException(CLIENT_NOT_FOUND_MESSAGE(clientId), CLIENT_NOT_FOUND);
        if (!discountService.existsById(dto.getDiscountId()))
            throw new ResourceNotFoundException(DISCOUNT_NOT_FOUND_MESSAGE(dto.getDiscountId()), DISCOUNT_NOT_FOUND);
        return ResponseEntity.ok(modelToDtoResponse(clientService.update(dto)));
    }

    @DeleteMapping("/{clientId}")
    // As I said earlier you can get rid of ResponseEntity altogether. But an important note here is "never use a generic wildcard with ResponseEntity"
    // You should always specify a type. It's either ResponseEntity<ClientDto> or ResponseEntity<Void>
    public ResponseEntity<?> delete(@PathVariable Long clientId) {
        if (!clientService.existsById(clientId))
            throw new ResourceNotFoundException(CLIENT_NOT_FOUND_MESSAGE(clientId), CLIENT_NOT_FOUND);
        clientService.delete(clientId);
        return ResponseEntity.ok(CLIENT_SUCCESSFULLY_DELETED);
    }
}
