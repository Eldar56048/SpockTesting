package com.example.spocktesting.api.facade

import com.example.spocktesting.dto.request.client.ClientCreateDtoRequest
import com.example.spocktesting.dto.request.client.ClientUpdateDtoRequest
import com.example.spocktesting.dto.response.client.ClientDtoResponse
import com.example.spocktesting.dto.response.discount.DiscountDtoResponse
import com.example.spocktesting.model.Client
import com.example.spocktesting.model.Discount
import com.example.spocktesting.util.facade.ClientFacade
import spock.lang.Specification

class ClientFacadeTest extends Specification{

    def "when we send Client object then method modelToDtoResponse return ClientDtoResponse object" () {
        given:
            def model = new Client(1, "Eldar", "Sairambay", "87013406863", new Discount(1, "100 Discount", 100))
            def expected = new ClientDtoResponse(1, "Eldar", "Sairambay", "87013406863", new DiscountDtoResponse(1, "100 Discount", 100))

        when:
            ClientDtoResponse actual = ClientFacade.modelToDtoResponse(model)

        then:
            expected == actual
    }

    def "when we send List of Client objects then method modelListToDtoResponseList return ClientDtoResponse List"() {
        given:
            def clients = createListOfClient()
            def expected = createListOfClientDtoResponse()

        when:
            def actual = ClientFacade.modelListToDtoResponseList(clients)

        then:
            expected == actual
    }

    def "when we send ClientCreateDtoRequest object then method createDtoRequestToModel return Client object"() {
        given:
            def dto = new ClientCreateDtoRequest("Eldar", "Sairambay", "87013406863", 1)
            def expected = new Client("Eldar", "Sairambay", "87013406863", null)

        when:
            def actual = ClientFacade.createDtoRequestToModel(dto)

        then:
            expected == actual
    }

    def "when we send ClientUpdateDtoRequest object and Client then method updateDtoRequestToModel return Client Object"() {
        given:
            def discount = new Discount(1, "100 Discount", 100)
            def client = new Client(1, "Eldar", "Sairambay", "87013406863", discount)
            def updateDto = new ClientUpdateDtoRequest(1, "IBNSINA", "Sairambay", "87018406823", 1)
            def expected = new Client(1, "IBNSINA", "Sairambay", "87018406823", discount)

        when:
            def actual = ClientFacade.updateDtoRequestToModel(client, updateDto)

        then:
            expected == actual
    }

    def createListOfClient() {
        def clients = new ArrayList()
        clients.add(new Client(1, "Eldar", "Sairambay", "87013406863", new Discount(1, "Standard", 0)))
        clients.add(new Client(2, "Baurzhan", "Mustapaev", "87027406803", new Discount(2, "VIP", 50)))
        clients.add(new Client(3, "Batyrkhan", "Alpanov", "87015721128", new Discount(2, "VIP", 50)))
        return clients
    }

    def createListOfClientDtoResponse() {
        def clients = new ArrayList()
        clients.add(new ClientDtoResponse(1, "Eldar", "Sairambay", "87013406863", new DiscountDtoResponse(1, "Standard", 0)))
        clients.add(new ClientDtoResponse(2, "Baurzhan", "Mustapaev", "87027406803", new DiscountDtoResponse(2, "VIP", 50)))
        clients.add(new ClientDtoResponse(3, "Batyrkhan", "Alpanov", "87015721128", new DiscountDtoResponse(2, "VIP", 50)))
        return clients
    }



}
