package com.trendyol.recomengine.webservice.controllers;

import com.trendyol.recomengine.webservice.engine.Producer;
import com.trendyol.recomengine.webservice.resource.Review;
import com.trendyol.recomengine.webservice.resource.ReviewWithoutUserId;
import com.trendyol.recomengine.webservice.resource.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class ReviewController {

    private final Producer producer;

    @Autowired
    ReviewController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/users/{userId}/reviews")
    public Object sendMessageToKafkaTopic(@RequestBody ReviewWithoutUserId requestBody, @PathVariable String userId) {
        Review review = new Review(userId, requestBody);

        System.out.print(String.format("user id: %s - ", userId));
        System.out.print(String.format("review user id: %s - ", review.getUserId()));
        System.out.println(String.format("timestamp: %d", requestBody.getTimestamp().getTime()));

        ValidationError validationError = validateReview(review);
        if (validationError != null) {
            return validationError;
        }

        String dataToSendToKafka = String.format("%s,%s,%.1f,%d", userId, requestBody.getProductId(), requestBody.getScore(), requestBody.getTimestamp().getTime());
        this.producer.sendMessage(dataToSendToKafka);

        return review;
    }

    private ValidationError validateReview(Review review) {
        if (!review.getUserId().matches("^[a-zA-Z0-9]+$")) {
            return ValidationError.generateInvalidUserIdError();
        }

        if (!review.getProductId().matches("^[a-zA-Z0-9]+$")) {
            return ValidationError.generateInvalidProductIdError();
        }

        if (review.getScore() < 0 || review.getScore() > 5) {
            return ValidationError.generateInvalidScoreError();
        }

        if (review.getTimestamp().getTime() <= 0) {
            return ValidationError.generateInvalidTimestampError();
        }

        return null;
    }
}
