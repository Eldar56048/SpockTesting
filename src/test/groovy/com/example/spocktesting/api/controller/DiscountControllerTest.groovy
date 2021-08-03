package com.example.spocktesting.api.controller

import com.example.spocktesting.controller.DiscountController
import com.example.spocktesting.dto.request.discount.DiscountCreateDtoRequest
import com.example.spocktesting.dto.request.discount.DiscountUpdateDtoRequest
import com.example.spocktesting.exception.GlobalExceptionHandler
import com.example.spocktesting.exception.domain.DtoException
import com.example.spocktesting.exception.domain.ResourceNotFoundException
import com.example.spocktesting.model.Discount
import com.example.spocktesting.service.implementation.DiscountServiceImpl
import groovy.json.JsonOutput
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static com.example.spocktesting.constant.ResponseCode.*

class DiscountControllerTest extends Specification{

    DiscountServiceImpl discountService = Mock(DiscountServiceImpl)
    MockMvc mvc = standaloneSetup(new DiscountController(discountService))
            .setControllerAdvice(new GlobalExceptionHandler())
            .build()

    def "when get '/' performed then the response has status 200 and content is JSON array"() {
        given:
            discountService.getAll() >> createListOfDiscount()

        when:
            def response = mvc.perform(get("/api/v1/discounts"))

        then:
            response
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentType == MediaType.APPLICATION_JSON.toString()
    }

    def "when put '/api/v1/discounts' performed then discount created and response has status 200 and content is JSON discount" () {
        given:
            def dto = new DiscountCreateDtoRequest("VIP", 50)
            def discount = new Discount(1, "VIP", 50)
        and:
            discountService.create(dto) >> discount

        when:
            def response = mvc.perform(
                    put("/api/v1/discounts")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(JsonOutput.toJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
            )

        then:
            response
                    .andExpect(status().isOk())
                    .andExpect(jsonPath('$.id', is(1)) as ResultMatcher)
                    .andExpect(jsonPath('$.discountName', is("VIP")) as ResultMatcher)
                    .andExpect(jsonPath('$.percentage', is(50)) as ResultMatcher)
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }

    def "when get '/api/v1/discounts/:id' performed then return discount and response has status 200 and content is JSON discount"() {
        given:
            def discount = new Discount(1, "VIP", 50)
        and:
            discountService.existsById(discount.id) >> true
            discountService.get(discount.id) >> discount

        when:
            def response = mvc.perform(get("/api/v1/discounts/"+discount.id))

        then:
            response
                    .andExpect(status().isOk())
                    .andExpect(jsonPath('$.id', is(discount.id)) as ResultMatcher)
                    .andExpect(jsonPath('$.discountName', is(discount.discountName)) as ResultMatcher)
                    .andExpect(jsonPath('$.percentage', is(discount.percentage)) as ResultMatcher)
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }

    def "when get '/api/v1/discounts/:id' performed then throw exception and response has status 404 and content is Exception"() {
        given:
            def id = 1
        and:
            discountService.existsById(id) >> false

        when:
            def response = mvc.perform(get("/api/v1/discounts/"+id))

        then:
            response
                    .andExpect(status().isNotFound())
                    .andExpect({ result -> result.getResolvedException() instanceof ResourceNotFoundException })
                    .andExpect(jsonPath('$.status', is(HttpStatus.NOT_FOUND.value())) as ResultMatcher)
                    .andExpect(jsonPath('$.code', is(DISCOUNT_NOT_FOUND)) as ResultMatcher)
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }

    def "when post '/api/v1/discounts/:id' performed then discount updated and response has status 200 and content is Json object of Discount"() {
        given:
            def updateDto = new DiscountUpdateDtoRequest(1, "VIP1", 60)
            def discount = new Discount(1, "VIP1", 60)
        and:
            discountService.existsById(updateDto.id) >>true
            discountService.update(updateDto) >> discount

        when:
            def response = mvc.perform(
                    post("/api/v1/discounts/"+updateDto.id)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(JsonOutput.toJson(updateDto))
                            .contentType(MediaType.APPLICATION_JSON)
            )

        then:
            response
                    .andExpect(status().isOk())
                    .andExpect(jsonPath('$.id', is(updateDto.id)) as ResultMatcher)
                    .andExpect(jsonPath('$.discountName', is(updateDto.discountName)) as ResultMatcher)
                    .andExpect(jsonPath('$.percentage', is(updateDto.percentage)) as ResultMatcher)
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }

    def "when post '/api/v1/discounts/:id' performed then thrown DtoException because two another id and response has status 400 and content is JSON Exception"() {
        given:
            def updateDto = new DiscountUpdateDtoRequest(1, "VIP1", 60)

        when:
        def response = mvc.perform(
                post("/api/v1/discounts/"+2)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonOutput.toJson(updateDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )

        then:
            response
                    .andExpect(status().isBadRequest())
                    .andExpect({ result -> result.getResolvedException() instanceof DtoException })
                    .andExpect(jsonPath('$.status', is(HttpStatus.BAD_REQUEST.value())) as ResultMatcher)
                    .andExpect(jsonPath('$.code', is(DISCOUNT_TWO_ANOTHER_ID)) as ResultMatcher)
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }

    def "when delete '/api/v1/discounts/:id' performed then discount deleted and response has status 200 and content is success message"() {
        given:
            def id = 1
        and:
            discountService.existsById(id) >> true

        when:
            def response = mvc.perform(delete("/api/v1/discounts/"+id))

        then:
            1 * discountService.delete(id)
            response
                    .andExpect(status().isOk())
                    .andReturn()
                    .response
                    .contentAsString == DISCOUNT_SUCCESSFULLY_DELETED
    }

    def "when delete '/api/v1/discounts/:id' performed then thrown ResourceNotFoundException because discount with this id not found and response has status 404 and content is JSON Exception"() {
        given:
            def id = 1
        and:
            discountService.existsById(id) >> false
        when:
            def response = mvc.perform(delete("/api/v1/discounts/"+id))

        then:
            response
                    .andExpect(status().isNotFound())
                    .andExpect({ result -> result.getResolvedException() instanceof ResourceNotFoundException })
                    .andExpect(jsonPath('$.status', is(HttpStatus.NOT_FOUND.value())) as ResultMatcher)
                    .andExpect(jsonPath('$.code', is(DISCOUNT_NOT_FOUND)) as ResultMatcher)
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }



    def createListOfDiscount() {
        def discounts = new ArrayList()
        discounts.add(new Discount(1, "VIP", 50))
        discounts.add(new Discount(2, "Standard", 0))
        discounts.add(new Discount(3, "SuperVIP", 100))
        return discounts
    }
}
