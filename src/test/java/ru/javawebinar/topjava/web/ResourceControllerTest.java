package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    void styleCss() throws Exception {
        perform(MockMvcRequestBuilders.get("/resources/css/style.css"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("text/css"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
