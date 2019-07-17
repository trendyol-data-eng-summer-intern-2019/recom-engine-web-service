package com.trendyol.recomengine.webservice.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecommendationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRecommendationsShouldReturnUserIdAndRecommendations() throws Exception {
        String userId = "3";
        this.mockMvc
                .perform(get("/users/" + userId + "/recommendations"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8),
                        jsonPath("$._id").value(userId),
                        jsonPath("$.recommendations").isArray()
                ));
    }
}