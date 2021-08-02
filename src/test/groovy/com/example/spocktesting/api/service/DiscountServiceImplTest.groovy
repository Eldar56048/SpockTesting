package com.example.spocktesting.api.service

import com.example.spocktesting.dto.request.discount.DiscountCreateDtoRequest
import com.example.spocktesting.dto.request.discount.DiscountUpdateDtoRequest
import com.example.spocktesting.exception.domain.DtoException
import com.example.spocktesting.model.Discount
import com.example.spocktesting.repository.DiscountRepository
import com.example.spocktesting.service.implementation.DiscountServiceImpl
import spock.lang.Specification
import spock.lang.Subject

class DiscountServiceImplTest extends Specification{

    @Subject
    DiscountServiceImpl service
    DiscountRepository repository = Mock(DiscountRepository)

    def setup() {
        service = new DiscountServiceImpl(repository)
    }

    def "method getAll should return list of discounts"() {
        given:
            def expected = createListOfDiscount()
        and:
            repository.findAll() >> createListOfDiscount()

        when:
            def actual = service.getAll()

        then:
            expected == actual
    }

    def "create discount method test"() {
        given:
            def createDto = new DiscountCreateDtoRequest("VIP", 50)
            def expected = new Discount(1, "VIP", 50)
        and:
            repository.save(_ as Discount) >> expected

        when:
            def actual = service.create(createDto)

        then:
            actual.id != null
            actual.discountName == createDto.discountName
            actual.percentage == createDto.percentage
    }

    def "when create discount method should throw DtoException because method existByDiscountName return true"() {
        given:
            def createDto = new DiscountCreateDtoRequest("VIP", 50)
        and:
            repository.existsByDiscountName(createDto.discountName) >> true

        when:
            service.create(createDto)

        then:
            thrown(DtoException)
    }

    def "when create discount method should throw DtoException because method existByPercentage return true"() {
        given:
            def createDto = new DiscountCreateDtoRequest("VIP", 50)
        and:
            repository.existsByPercentage(createDto.percentage) >> true

        when:
            service.create(createDto)

        then:
            thrown(DtoException)
    }

    def "update Discount method test"() {
        given:
            def updateDto = new DiscountUpdateDtoRequest(1, "VIP NEW", 60)
            def expected = new Discount(1, "VIP NEW", 60)
        and:
            repository.getById(1) >> new Discount(1, "VIP", 50)
            repository.save(_ as Discount) >> { Discount discount -> discount }

        when:
            def actual = service.update(updateDto)

        then:
            expected == actual
    }

    def "when update discount method should throw DtoException because method existByDiscountNameAndIdNotLike return true"() {
        given:
            def updateDto = new DiscountUpdateDtoRequest(1, "VIP NEW", 60)
        and:
            repository.existsByDiscountNameAndIdNotLike(updateDto.discountName, updateDto.id) >> true

        when:
            service.update(updateDto)

        then:
            thrown(DtoException)
    }

    def "when update discount method should throw DtoException because method existByPercentageAndIdNotLike return true"() {
        given:
            def updateDto = new DiscountUpdateDtoRequest(1, "VIP NEW", 60)
        and:
            repository.existsByPercentageAndIdNotLike(updateDto.percentage, updateDto.id) >> true

        when:
            service.update(updateDto)

        then:
            thrown(DtoException)
    }

        def "get Discount method test"() {
        given:
            def id = 1
            def expected = new Discount(1, "VIP", 50)
        and:
            repository.getById(id) >> expected

        when:
            def actual = service.get(id)

        then:
            expected == actual
    }

    def "delete Discount test"() {
        given:
            def id = 1

        when:
            service.delete(id)

        then:
            1 * repository.deleteById(id)
    }

    def "existById method should return repositories returned value"() {
        given:
            def id = 1
        and:
            repository.existsById(id) >> true

        when:
            def actual = service.existsById(id)

        then:
            actual
    }

    def "existById method test verify the call repository method"() {
        given:
            def id = 1

        when:
            service.existsById(id)

        then:
            1 * repository.existsById(id)
    }

    def "save discount method test verify the call repository method"() {
        given:
            def discount = new Discount(1, "VIP", 50)

        when:
            service.save(discount)

        then:
            1 * repository.save(discount)
    }

    def "save method should return repositories returned value"() {
        given:
            def discount = new Discount(1, "VIP", 50)
        and:
            repository.save(discount) >> {Discount model -> model}

        when:
            def actual = service.save(discount)

        then:
            discount == actual
    }

    def "existsByDiscountName method test verify the call repository method"() {
        given:
            def discountName = "VIP"

        when:
            service.existsByDiscountName(discountName)

        then:
            1 * repository.existsByDiscountName(discountName)
    }

    def "existsByDiscountName method should return repositories returned value"() {
        given:
            def discountName = "VIP"
        and:
            repository.existsByDiscountName(discountName) >> true

        when:
            def actual = service.existsByDiscountName(discountName)

        then:
            actual
    }

    def "existsByDiscountNameAndIdNotLike method test verify the call repository method"() {
        given:
            def id = 1
            def discountName = "VIP"

        when:
            service.existsByDiscountNameAndIdNotLike(discountName, id)

        then:
            1 * repository.existsByDiscountNameAndIdNotLike(discountName, id)
    }

    def "existsByDiscountNameAndIdNotLike method should return repositories returned value"() {
        given:
            def id = 1
            def discountName = "VIP"
        and:
            repository.existsByDiscountNameAndIdNotLike(discountName, id) >> true

        when:
            def actual = service.existsByDiscountNameAndIdNotLike(discountName, id)

        then:
            actual
    }

    def "existsByPercentage method test verify the call repository method"() {
        given:
            def percentage = 50

        when:
            service.existsByPercentage(percentage)

        then:
            1 * repository.existsByPercentage(percentage)
    }

    def "existsByPercentage method should return repositories returned value"() {
        given:
            def percentage = 50
        and:
            repository.existsByPercentage(percentage) >> true

        when:
            def actual = service.existsByPercentage(percentage)

        then:
            actual
    }


    def "existsByPercentageAndIdNotLike method test verify the call repository method"() {
        given:
            def id = 1
            def percentage = 50

        when:
            service.existsByPercentageAndIdNotLike(percentage, id)

        then:
            1 * repository.existsByPercentageAndIdNotLike(percentage, id)
    }

    def "existsByPercentageAndIdNotLike method should return repositories returned value"() {
        given:
            def id = 1
            def percentage = 50
        and:
            repository.existsByPercentageAndIdNotLike(percentage, id) >> true

        when:
            def actual = service.existsByPercentageAndIdNotLike(percentage, id)

        then:
            actual
    }

    def createListOfDiscount() {
        def discounts = new ArrayList()
        discounts.add(new Discount(1, "VIP", 50))
        discounts.add(new Discount(2, "Standard", 0))
        discounts.add(new Discount(3, "SuperVIP", 100))
        return discounts
    }

}
