package com.trendyol.recomengine.webservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.recomengine.webservice.resource.Review;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void createReviewShouldReturnTheGivenReviewBack() throws Exception {
        String userId = "3";
        Timestamp t = new Timestamp(10);
        String time = getTimeStringFormatted(t);
        Review review = new Review(userId, "5", 3.5F, t);

        this.mockMvc.perform(post("/users/"+userId+"/reviews")
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() {
        Mockito.when(kafkaTemplate.sendDefault("3,5,3.5,10")).thenReturn(new ListenableFuture<SendResult<String, String>>() {
            @Override
            public void addCallback(ListenableFutureCallback<? super SendResult<String, String>> callback) {

            }

            @Override
            public void addCallback(SuccessCallback<? super SendResult<String, String>> successCallback, FailureCallback failureCallback) {

            }

            @Override
            public boolean cancel(boolean b) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public SendResult<String, String> get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public SendResult<String, String> get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        });/*.then((Answer<String>) invocation -> {
            System.out.println("3,5,3.5,10 -- printed");
            return "Do nothing.";
        });*/
    }
}
