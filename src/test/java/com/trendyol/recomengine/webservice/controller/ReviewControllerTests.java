package com.trendyol.recomengine.webservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.recomengine.webservice.model.Review;
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
public class ReviewControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createReviewShouldReturnTheGivenReviewBack() throws Exception {
        String userId = "3";
        Timestamp t = new Timestamp(10);
        String time = getTimeStringFormatted(t);
        Review review = new Review(userId, "5", 3.5F, t);
        given(kafkaTemplate.sendDefault("3,5,3.5,10")).willReturn(null);
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

    private String getTimeStringFormatted(Timestamp time) {
        String timeStringFormatted;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println("GMT time: " + sdf.format(time));
        timeStringFormatted = sdf.format(time);
        return timeStringFormatted;
    }
}
