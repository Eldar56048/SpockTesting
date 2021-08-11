package com.example.spocktesting.controller;

import com.example.spocktesting.dto.request.discount.DiscountCreateDtoRequest;
import com.example.spocktesting.dto.request.discount.DiscountUpdateDtoRequest;
import com.example.spocktesting.exception.domain.DtoException;
import com.example.spocktesting.exception.domain.ResourceNotFoundException;
import com.example.spocktesting.model.Discount;
import com.example.spocktesting.service.implementation.DiscountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.example.spocktesting.util.facade.DiscountFacade.*;
import static com.example.spocktesting.constant.ResponseCode.*;
import static com.example.spocktesting.constant.ResponseCodeMessage.*;

import javax.validation.Valid;

// All the notes from ClientController apply here as well
@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {
    // Again autowire by interface
    private final DiscountServiceImpl service;

    @Autowired
    public DiscountController(DiscountServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(modelListToDtoResponseList(service.getAll()));
    }

    @PutMapping
    public ResponseEntity<?> create(@Valid @RequestBody DiscountCreateDtoRequest dto) {
        return ResponseEntity.ok(modelToDtoResponse(service.create(dto)));
    }

    @GetMapping("/{discountId}")
    public ResponseEntity<?> get(@PathVariable Long discountId) {
        if (!service.existsById(discountId))
            throw new ResourceNotFoundException(DISCOUNT_NOT_FOUND_MESSAGE(discountId), DISCOUNT_NOT_FOUND);
        return ResponseEntity.ok(modelToDtoResponse(service.get(discountId)));
    }

    @PostMapping("/{discountId}")
    public ResponseEntity<?> update(@PathVariable Long discountId, @Valid @RequestBody DiscountUpdateDtoRequest dto) {
        if (dto.getId()!=discountId)
            throw new DtoException("Два разных id", DISCOUNT_TWO_ANOTHER_ID);
        if (!service.existsById(discountId))
            throw new ResourceNotFoundException(DISCOUNT_NOT_FOUND_MESSAGE(discountId), DISCOUNT_NOT_FOUND);
        return ResponseEntity.ok(modelToDtoResponse(service.update(dto)));
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<?> delete(@PathVariable Long discountId){
        if (!service.existsById(discountId))
            throw new ResourceNotFoundException(DISCOUNT_NOT_FOUND_MESSAGE(discountId), DISCOUNT_NOT_FOUND);
        service.delete(discountId);
        return ResponseEntity.ok(DISCOUNT_SUCCESSFULLY_DELETED);
    }
}
