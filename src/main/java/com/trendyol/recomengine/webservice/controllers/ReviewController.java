package com.trendyol.recomengine.webservice.controllers;

import com.trendyol.recomengine.webservice.engine.Producer;
import com.trendyol.recomengine.webservice.resource.Review;
import com.trendyol.recomengine.webservice.resource.ReviewWithoutUserId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Handles POST requests that contain user reviews, validates the data that comes from requests and sends them to Kafka.
 */
@RestController
public class ReviewController {

    private final Producer producer;

    ReviewController(Producer producer) {
        this.producer = producer;
    }

    /**
     * This method is called when a POST request happens. Validates the data in the request body using validateReview
     * and sends them to Kafka.
     * @param requestBody POST request's body which is converted to a ReviewWithoutUserId object.
     * @param userId      The user's id who sends the POST request.
     * @return If the request body is a valid review, the method returns the same review that is posted. Otherwise,
     * the method returns an error message which tells why the request body is invalid.
     * @see ReviewWithoutUserId
     */
    @PostMapping(value = "/users/{userId}/reviews")
    public Object sendMessageToKafkaTopic(@Valid @RequestBody ReviewWithoutUserId requestBody, @PathVariable String userId) {
        Review review = new Review(userId, requestBody);

        String dataToSendToKafka = String.format("%s,%s,%.1f,%d", userId, requestBody.getProductId(),
                requestBody.getScore(), requestBody.getTimestamp().getTime());
        this.producer.sendMessage(dataToSendToKafka);

        return review;
    }
}
