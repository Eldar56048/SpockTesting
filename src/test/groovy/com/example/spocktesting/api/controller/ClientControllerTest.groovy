package com.example.spocktesting.api.controller

import com.example.spocktesting.controller.ClientController
import com.example.spocktesting.controller.DiscountController
import com.example.spocktesting.dto.request.client.ClientCreateDtoRequest
import com.example.spocktesting.dto.request.discount.DiscountCreateDtoRequest
import com.example.spocktesting.exception.GlobalExceptionHandler
import com.example.spocktesting.exception.domain.ResourceNotFoundException
import com.example.spocktesting.model.Client
import com.example.spocktesting.model.Discount
import com.example.spocktesting.service.implementation.ClientServiceImpl
import com.example.spocktesting.service.implementation.DiscountServiceImpl
import groovy.json.JsonOutput
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import spock.lang.Specification

import static com.example.spocktesting.constant.ResponseCode.CLIENT_NOT_FOUND
import static com.example.spocktesting.constant.ResponseCode.DISCOUNT_NOT_FOUND
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ClientControllerTest extends Specification{

    ClientServiceImpl clientService = Mock(ClientServiceImpl)
    DiscountServiceImpl discountService = Mock(DiscountServiceImpl)

    MockMvc mvc = standaloneSetup(new ClientController(clientService, discountService))
            .setControllerAdvice(new GlobalExceptionHandler())
            .build()

    def "when get '/' performed then the response has status 200 and content is JSON array"() {
        given:
            clientService.getAll() >> createListOfClient()

        when:
            def response = mvc.perform(get("/api/v1/clients"))

        then:
            response
                    .andExpect(status().isOk())
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }

    def "when put '/api/v1/clients' performed then client created and response has status 200 and content is JSON client" () {
        given:
            def createDto = new ClientCreateDtoRequest("Eldar", "Sairambay", "87018406823", 1)
            def client = new Client("Eldar", "Sairambay", "87018406823", new Discount(1, "VIP", 50))
        and:
            discountService.existsById(createDto.discountId) >> true
            clientService.create(createDto) >> client

        when:
            def response = mvc.perform(
                    put("/api/v1/clients")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(JsonOutput.toJson(createDto))
                            .contentType(MediaType.APPLICATION_JSON)
            )

        then:
            response
                    .andExpect(status().isOk())
                    .andExpect(jsonPath('$.name', is(createDto.name)) as ResultMatcher)
                    .andExpect(jsonPath('$.surname', is(createDto.surname)) as ResultMatcher)
                    .andExpect(jsonPath('$.phoneNumber', is(createDto.phoneNumber)) as ResultMatcher)
                    .andExpect(jsonPath('$.discount.id', is(createDto.discountId)) as ResultMatcher)
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }

    def "when put '/api/v1/clients' performed then thrown ResourceNotFoundException because discount with this id not found and response has status 404 and content is JSON Exception"() {
        given:
            def createDto = new ClientCreateDtoRequest("Eldar", "Sairambay", "87018406823", 1)

        and:
            discountService.existsById(createDto.discountId) >> false

        when:
            def response = mvc.perform(
                    put("/api/v1/clients")
                            .accept(MediaType.APPLICATION_JSON)
                            .content(JsonOutput.toJson(createDto))
                            .contentType(MediaType.APPLICATION_JSON)
            )

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

    def "when get '/api/v1/clients/:id' performed then return client and response has status 200 and content is JSON client"() {
        given:
            def client = new Client(1, "Eldar", "Sairambay", "87013406863", new Discount(1, "Standard", 0))
        and:
            clientService.existsById(client.id) >> true
            clientService.get(client.id) >> client

        when:
            def response = mvc.perform(get("/api/v1/clients/"+client.id))

        then:
            response
                    .andExpect(status().isOk())
                    .andExpect(jsonPath('$.id', is(client.id)) as ResultMatcher)
                    .andExpect(jsonPath('$.name', is(client.name)) as ResultMatcher)
                    .andExpect(jsonPath('$.surname', is(client.surname)) as ResultMatcher)
                    .andExpect(jsonPath('$.phoneNumber', is(client.phoneNumber)) as ResultMatcher)
                    .andExpect(jsonPath('$.discount.id', is(client.discount.id)) as ResultMatcher)
                    .andExpect(jsonPath('$.discount.discountName', is(client.discount.discountName)) as ResultMatcher)
                    .andExpect(jsonPath('$.discount.percentage', is(client.discount.percentage)) as ResultMatcher)
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }

    def "when get '/api/v1/clients/:id' performed then thrown ResourceNotFoundException because client with this id not found and response has status 404 and content is JSON Exception"() {
        given:
            def id = 1
        and:
            clientService.existsById(id) >> false

        when:
            def response = mvc.perform(get("/api/v1/clients/"+id))

        then:
            response
                    .andExpect(status().isNotFound())
                    .andExpect({ result -> result.getResolvedException() instanceof ResourceNotFoundException })
                    .andExpect(jsonPath('$.status', is(HttpStatus.NOT_FOUND.value())) as ResultMatcher)
                    .andExpect(jsonPath('$.code', is(CLIENT_NOT_FOUND)) as ResultMatcher)
                    .andReturn()
                    .response
                    .contentType == MediaType.APPLICATION_JSON.toString()
    }





    def createListOfClient() {
        def clients = new ArrayList()
        clients.add(new Client(1, "Eldar", "Sairambay", "87013406863", new Discount(1, "Standard", 0)))
        clients.add(new Client(2, "Baurzhan", "Mustapaev", "87027406803", new Discount(2, "VIP", 50)))
        clients.add(new Client(3, "Batyrkhan", "Alpanov", "87015721128", new Discount(2, "VIP", 50)))
        return clients
    }

}
