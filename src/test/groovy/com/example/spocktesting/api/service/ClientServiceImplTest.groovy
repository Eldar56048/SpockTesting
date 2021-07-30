package com.example.spocktesting.api.service

import com.example.spocktesting.dto.request.client.ClientCreateDtoRequest
import com.example.spocktesting.dto.request.client.ClientUpdateDtoRequest
import com.example.spocktesting.model.Client
import com.example.spocktesting.model.Discount
import com.example.spocktesting.repository.ClientRepository
import com.example.spocktesting.service.implementation.ClientServiceImpl
import com.example.spocktesting.service.implementation.DiscountServiceImpl
import spock.lang.Specification
import spock.lang.Subject

class ClientServiceImplTest extends Specification {
    @Subject
    ClientServiceImpl service
    ClientRepository repository = Mock(ClientRepository)
    DiscountServiceImpl discountService = Mock(DiscountServiceImpl)

    def setup() {
        service = new ClientServiceImpl(repository, discountService)
    }

    def "method getAll should return list of Client object"() {
        given:
            def expected = createListOfClient()
        and:
            repository.findAll() >> createListOfClient()

        when:
            def actual = service.getAll()

        then:
            expected == actual
    }

    def "create Client method test"() {
        given:
            def createDto = new ClientCreateDtoRequest("Eldar", "Sairambay", "87018406823", 1)
            def discount = new Discount(1, "VIP", 50)
            def expected = new Client(null, "Eldar", "Sairambay", "87018406823", discount)
        and:
            discountService.get(discount.id) >> discount
            repository.save(_ as Client) >> { Client client -> client }

        when:
            def actual = service.create(createDto)

        then:
            expected == actual
    }

    def "update client method test" () {
        given:
            def updateDto = new ClientUpdateDtoRequest(1, "Eldar", "Sairambay", "87018406823", 2)
            def client = new Client(1, "Eldar", "Sairambay", "87013406863", new Discount(1, "VIP", 50))
            def discount = new Discount(2, "Super VIP", 100)
            def expected = new Client(1, "Eldar", "Sairambay", "87018406823", discount)
        and:
            repository.getById(updateDto.id) >> client
            discountService.get(discount.id) >> discount
            repository.save(_ as Client) >> {Client model -> model}

        when:
            def actual = service.update(updateDto)

        then:
            expected == actual
    }

    def "delete client method test"() {
        given:
            def id = 1

        when:
            service.delete(id)

        then:
            1 * repository.deleteById(id)
    }

    def "existById method test"() {
        given:
            def id = 1

        when:
            service.existsById(id)

        then:
            1 * repository.existsById(id)
    }

    def "save client method test verify the call repository method"() {
        given:
            def client = new Client(1, "Eldar", "Sairambay", "87013406863", new Discount(1, "VIP", 50))

        when:
            service.save(client)

        then:
            1 * repository.save(client)
    }

    def "when we send id then method get should return client"() {
        given:
            def id = 1
            def expected = new Client(1, "Eldar", "Sairambay", "87018406823", new Discount(1, "VIP", 50))
        and:
            repository.getById(id) >> expected

        when:
            def actual = service.get(id)

        then:
            expected == actual
    }

    def "existsById method test verify the call repository method"() {
        given:
            def id = 1

        when:
            service.existsById(id)

        then:
            1 * repository.existsById(id)

    }

    def "existsById method should return repositories returned value"() {
        given:
            def id = 1
        and:
            repository.existsById(id) >> true

        when:
            def actual = service.existsById(id)

        then:
            actual
    }

    def createListOfClient() {
        def clients = new ArrayList()
        clients.add(new Client(1, "Eldar", "Sairambay", "87013406863", new Discount(1, "Standard", 0)))
        clients.add(new Client(2, "Baurzhan", "Mustapaev", "87027406803", new Discount(2, "VIP", 50)))
        clients.add(new Client(3, "Batyrkhan", "Alpanov", "87015721128", new Discount(2, "VIP", 50)))
        return clients
    }
}
