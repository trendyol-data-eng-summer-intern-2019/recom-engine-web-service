package com.trendyol.recomengine.webservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.recomengine.webservice.model.Review;
import com.trendyol.recomengine.webservice.repository.RecommendationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void createReviewShouldReturnTheGivenReviewBack() throws Exception {
        String userId = "3";
        Timestamp timestamp = new Timestamp(10);
        String time = getTimeStringFormatted(timestamp);
        Review review = new Review(userId, "5", 3.5F, timestamp);
        this.mockMvc.perform(post("/users/" + userId + "/reviews")
                .content(asJsonString(review))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON_UTF8),
                        jsonPath("$.userId").value(userId),
                        jsonPath("$.productId").value(review.getProductId()),
                        jsonPath("$.score").value(review.getScore()),
                        jsonPath("$.timestamp").value(time)
                ));
    }

    @Test
    public void createReviewShouldReturnBadRequestSinceScoreIsNotValid() throws Exception {
        String userId = "3";
        String productId = "5";
        float score = 6F;
        Timestamp timestamp = new Timestamp(10);
        Review review = new Review(userId, productId, score, timestamp);
        this.mockMvc.perform(post("/users/" + userId + "/reviews")
                .content(asJsonString(review))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createReviewShouldReturnNotFoundSinceUserIdIsEmpty() throws Exception {
        String userId = "";
        Timestamp timestamp = new Timestamp(10);
        String time = getTimeStringFormatted(timestamp);
        Review review = new Review(userId, "5", 3.5F, timestamp);
        this.mockMvc.perform(post("/users/" + userId + "/reviews")
                .content(asJsonString(review))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createReviewShouldReturnBadRequestSinceAllFieldsDoNotExist() throws Exception {
        String uId = "3";
        Object review = new Object() {
            public final String userId = uId;
            public final String productId = "5";
            public final Timestamp timestamp = new Timestamp(10);
        };
        this.mockMvc.perform(post("/users/" + uId + "/reviews")
                .content(asJsonString(review))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getTimeStringFormatted(Timestamp time) {
        String timeStringFormatted;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println("GMT time: " + sdf.format(time));
        timeStringFormatted = sdf.format(time);
        return timeStringFormatted;
    }
}
