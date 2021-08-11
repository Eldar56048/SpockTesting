package com.example.spocktesting.util.facade;

import com.example.spocktesting.dto.request.discount.DiscountCreateDtoRequest;
import com.example.spocktesting.dto.request.discount.DiscountUpdateDtoRequest;
import com.example.spocktesting.dto.response.discount.DiscountDtoResponse;
import com.example.spocktesting.model.Discount;

import java.util.ArrayList;
import java.util.List;

// what I've written earlier about static methods applies here as well
// learn about mapper pattern, it seems more suitable for the purpose
public class DiscountFacade {

    public static DiscountDtoResponse modelToDtoResponse(Discount model) {
        DiscountDtoResponse dto = new DiscountDtoResponse();
        if (model.getId() != null)
            dto.setId(model.getId());
        if (model.getDiscountName() != null)
            dto.setDiscountName(model.getDiscountName());
        dto.setPercentage(model.getPercentage());
        return dto;
    }

    public static List<DiscountDtoResponse> modelListToDtoResponseList(List<Discount> modelList){
        List<DiscountDtoResponse> dtoList = new ArrayList<>();
        for(Discount model : modelList) {
            dtoList.add(modelToDtoResponse(model));
        }
        return dtoList;
    }

    public static Discount createDtoRequestToModel(DiscountCreateDtoRequest dto) {
        Discount model = new Discount();
        if (dto.getDiscountName() != null)
            model.setDiscountName(dto.getDiscountName());
        model.setPercentage(dto.getPercentage());
        return model;
    }

    public static Discount updateDtoRequestToModel(Discount model, DiscountUpdateDtoRequest dto) {
        if (dto.getDiscountName() != null)
            model.setDiscountName(dto.getDiscountName());
        model.setPercentage(dto.getPercentage());
        return model;
    }

}
