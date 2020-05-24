package com.truphone.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class FamiliyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFastGrowingFamily() throws Exception {
        mockMvc.perform(get("/api/families/fast-growing")
                .contentType("application/json"))
                .andExpect(status().isOk());

    }
}
