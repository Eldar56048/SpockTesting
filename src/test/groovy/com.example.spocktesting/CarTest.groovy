package com.example.spocktesting

import com.example.spocktesting.model.Car
import org.hibernate.internal.FastSessionServices
import spock.lang.Specification


class CarTest extends Specification {

    def myCar = Mock(Car)

    def "test If Car Is A Car"(){
        expect:
            assert myCar instanceof Car, "This object is not instance of Car"
    }

    def "method needsFuel should return true"() {
        given:
            myCar.needsFuel() >> true
        when:
            def result = myCar.needsFuel()
        then:
            result
        and:
            1 * myCar.needsFuel()
    }

    def "method needs fuel throw RuntimeException"() {
        given:
            myCar.needsFuel() >> { throw new RuntimeException () }
        when:
            myCar.needsFuel()
        then:
            thrown(RuntimeException)
    }




}