package com.example.spocktesting.api.facade

import com.example.spocktesting.dto.request.discount.DiscountCreateDtoRequest
import com.example.spocktesting.dto.request.discount.DiscountUpdateDtoRequest
import com.example.spocktesting.dto.response.discount.DiscountDtoResponse
import com.example.spocktesting.model.Discount
import com.example.spocktesting.util.facade.DiscountFacade
import spock.lang.Specification

class DiscountFacadeTest extends Specification{

    def "when we send Discount object method modelToDtoResponse return DiscountDtoResponse object"() {
        given:
            def discount = new Discount(1, "VIP", 50)
            def expected = new DiscountDtoResponse(1, "VIP", 50)

        when:
            def actual = DiscountFacade.modelToDtoResponse(discount)

        then:
            expected == actual
    }

    def "when we send List of Discount objects then method modelListToDtoResponseList return List of DiscountDtoResponse objects"() {
        given:
            def discounts = createListOfDiscount()
            def expected = createListOfDiscountDtoResponse()

        when:
            def actual = DiscountFacade.modelListToDtoResponseList(discounts)

        then:
            expected == actual
    }

    def "when we send DiscountCreateDtoRequest object then method createDtoRequestToModel return Discount object"() {
        given:
            def createDto = new DiscountCreateDtoRequest("VIP", 50)
            def expected = new Discount(null, "VIP", 50)

        when:
            def actual = DiscountFacade.createDtoRequestToModel(createDto)

        then:
            expected == actual
    }

    def "when we send DiscountUpdateDtoRequest object then method updateDtoRequestToModel return Discount object"() {
        given:
            def discount = new Discount(1, "Standard", 0)
            def updateDto = new DiscountUpdateDtoRequest(1, "VIP", 50)
            def expected = new Discount(1, "VIP", 50)

        when:
            def actual = DiscountFacade.updateDtoRequestToModel(discount, updateDto)

        then:
            expected == actual
    }

    def createListOfDiscount() {
        def discounts = new ArrayList()
        discounts.add(new Discount(1, "VIP", 50))
        discounts.add(new Discount(2, "Standard", 0))
        discounts.add(new Discount(3, "SuperVIP", 100))
        return discounts
    }

    def createListOfDiscountDtoResponse() {
        def discounts = new ArrayList()
        discounts.add(new DiscountDtoResponse(1, "VIP", 50))
        discounts.add(new DiscountDtoResponse(2, "Standard", 0))
        discounts.add(new DiscountDtoResponse(3, "SuperVIP", 100))
        return discounts
    }

}
