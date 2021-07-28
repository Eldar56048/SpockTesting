package com.example.spocktesting.service.implementation;

import com.example.spocktesting.dto.request.discount.DiscountCreateDtoRequest;
import com.example.spocktesting.dto.request.discount.DiscountUpdateDtoRequest;
import com.example.spocktesting.exception.domain.DtoException;
import com.example.spocktesting.model.Discount;
import com.example.spocktesting.repository.DiscountRepository;
import com.example.spocktesting.service.domain.DiscountService;
import static com.example.spocktesting.util.facade.DiscountFacade.*;
import static com.example.spocktesting.constant.ResponseCode.*;
import static com.example.spocktesting.constant.ResponseCodeMessage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository repository;

    @Autowired
    public DiscountServiceImpl(DiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Discount> getAll() {
        return repository.findAll();
    }

    @Override
    public Discount create(DiscountCreateDtoRequest dto) {
        if (existsByDiscountName(dto.getDiscountName()))
            throw new DtoException(DISCOUNT_EXISTS_BY_NAME_MESSAGE, DISCOUNT_EXISTS_BY_NAME);
        if (existsByPercentage(dto.getPercentage()))
            throw new DtoException(DISCOUNT_EXISTS_BY_PERCENT_MESSAGE, DISCOUNT_EXISTS_BY_PERCENT);
        Discount discount = createDtoRequestToModel(dto);
        return save(discount);
    }

    @Override
    public Discount update(DiscountUpdateDtoRequest dto) {
        if (existsByDiscountNameAndIdNotLike(dto.getDiscountName(), dto.getId()))
            throw new DtoException(DISCOUNT_EXISTS_BY_NAME_MESSAGE, DISCOUNT_EXISTS_BY_NAME);
        if (existsByPercentageAndIdNotLike(dto.getPercentage(), dto.getId()))
            throw new DtoException(DISCOUNT_EXISTS_BY_PERCENT_MESSAGE, DISCOUNT_EXISTS_BY_PERCENT);
        Discount discount = get(dto.getId());
        discount = updateDtoRequestToModel(discount, dto);
        return save(discount);
    }

    @Override
    public Discount get(Long id) {
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
    public Discount save(Discount model) {
        return repository.save(model);
    }

    @Override
    public boolean existsByDiscountName(String discountName) {
        return repository.existsByDiscountName(discountName);
    }

    @Override
    public boolean existsByDiscountNameAndIdNotLike(String discountName, Long id) {
        return repository.existsByDiscountNameAndIdNotLike(discountName, id);
    }

    @Override
    public boolean existsByPercentage(int percentage) {
        return repository.existsByPercentage(percentage);
    }

    @Override
    public boolean existsByPercentageAndIdNotLike(int percentage, Long id) {
        return repository.existsByPercentageAndIdNotLike(percentage, id);
    }


}
