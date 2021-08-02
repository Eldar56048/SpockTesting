package com.example.spocktesting.api.controller

import com.example.spocktesting.controller.WebController
import com.example.spocktesting.exception.GlobalExceptionHandler
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class WebControllerTest extends Specification {

    MockMvc mvc = standaloneSetup(new WebController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build()

    def "when get is performed then the response has status 200 and content is 'Hello world!'"() {
        when:
            def response = mvc.perform(get("/hello"))

        then:
            response
                    .andExpect(status().isOk())
                    .andReturn()
                    .response
                    .contentAsString == "Hello world!"
    }
}
