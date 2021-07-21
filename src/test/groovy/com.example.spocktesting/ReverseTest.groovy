package com.example.spocktesting

import com.example.spocktesting.util.Reverse
import org.mockito.internal.matchers.Null
import spock.lang.Specification

class ReverseTest extends Specification {

    def "method reverse should return reversed words"(String word, String reversedWord) {
        expect:
        Reverse.reverse(word) == reversedWord
        where:
            word    | reversedWord
            "Hello" | "olleH"
            "Eldar" | "radlE"
    }

    def "method reverse should return Null Pointer Exception"() {
        when:
            Reverse.reverse(null)
        then:
            thrown(NullPointerException)
    }

}
