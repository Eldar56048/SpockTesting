package com.example.spocktesting

import com.example.spocktesting.model.Money
import spock.lang.Specification

class MoneyTest extends Specification {

    def setup() {

    }

    def "constructor should set amount and currency"(int amount, String currency) {
        given:
            def money
        when:
            money = new Money(amount, currency)
        then:
            amount == money.getAmount()
            currency == money.getCurrency()
        where:
            amount | currency
            10     | "USD"
            25     | "EUR"
            350    | "RUB"
            10000  | "KZT"
    }

    def "equal method should return false"() {
        given:
            Object object
            Money money
        when:
            object = null
            money = new Money(10, "USD")
        then:
            false == money.equals(object)
    }

    def "constructor should throw Illegal Argument Exception for invalid data"(int amount, String currency) {
        when:
            new Money(amount, currency)
        then:
            thrown(IllegalArgumentException)
        where:
            amount | currency
            -12    | null
            25     | null
            -1     | "RUB"
    }




}
