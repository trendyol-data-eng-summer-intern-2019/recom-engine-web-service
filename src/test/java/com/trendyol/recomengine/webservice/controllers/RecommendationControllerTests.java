package com.trendyol.recomengine.webservice.controllers;

import com.trendyol.recomengine.webservice.repositories.RecommendationRepository;
import com.trendyol.recomengine.webservice.resource.Recommendation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.BDDMockito.given;
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

    @MockBean
    private RecommendationRepository repository;

    @Test
    public void getRecommendationShouldReturnUserIdAndRecommendations() throws Exception {
        String userId = "3";
        Recommendation mockResponse = new Recommendation(userId, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
        given(repository.findBy_id(userId)).willReturn(mockResponse);
        this.mockMvc
                .perform(get("/users/" + userId + "/recommendations"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8),
                        jsonPath("$._id").value(userId),
                        jsonPath("$.recommendations", hasItems(mockResponse.getRecommendations()))
                ));
    }
}