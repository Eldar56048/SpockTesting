package com.example.spocktesting.api.service

import com.example.spocktesting.dto.request.discount.DiscountCreateDtoRequest
import com.example.spocktesting.dto.request.discount.DiscountUpdateDtoRequest
import com.example.spocktesting.model.Discount
import com.example.spocktesting.repository.DiscountRepository
import com.example.spocktesting.service.implementation.DiscountServiceImpl
import org.mockito.InjectMocks
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
            1 * repository.findAll() >> createListOfDiscount()
        and:
            def expected = createListOfDiscount()
        when:
            def actual = service.getAll()
        then:
            expected == actual
    }

    def "create discount method test"() {
        given:
            def createDto = new DiscountCreateDtoRequest("VIP", 50)
        and:
            def expected = new Discount(1, "VIP", 50)
        and:
            1 * repository.save(_ as Discount) >> expected
        and:
            1 * repository.existsByDiscountName("VIP")
        and:
            1 * repository.existsByPercentage(50)
        when:
            def actual = service.create(createDto)
        then:
            actual.id != null
            actual.discountName == createDto.discountName
            actual.percentage == createDto.percentage
    }

    def "update Discount method test"() {
        given:
            def updateDto = new DiscountUpdateDtoRequest(1, "VIP NEW", 60)
        and:
            def expected = new Discount(1, "VIP NEW", 60)
        and:
            1 * repository.existsByDiscountNameAndIdNotLike(updateDto.discountName, updateDto.id)
        and:
            1 * repository.existsByPercentageAndIdNotLike(updateDto.percentage, updateDto.id)
        and:
            1 * repository.getById(1) >> new Discount(1, "VIP", 50)
        and:
            1 * repository.save(_ as Discount) >> { Discount discount -> discount }
        when:
            def actual = service.update(updateDto)
        then:
            expected == actual
    }

    def "get Discount method test"() {
        given:
            def id = 1
        and:
            def expected = new Discount(1, "VIP", 50)
        and:
            1 * repository.getById(id) >> expected
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

    def "existsById method test"() {
        given:
            def id = 1
        and:
        and:
            1 * repository.existsById(id) >> true
        when:
            def actual = service.existsById(id)
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
