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
 * Handles POST requests that contain user reviews, validates the data that comes from requests and sends them to Kafka.
 */
@RestController
public class ReviewController {

    private final Producer producer;

    @Autowired
    ReviewController(Producer producer) {
        this.producer = producer;
    }

    /**
     * This method is called when a POST request happens. Validates the data in the request body using validateReview
     * and sends them to Kafka.
     * @param requestBody POST request's body which is converted to a ReviewWithoutUserId object.
     * @param userId      The user's id who does the POST request.
     * @return If the request body is a valid review, the method returns the same review that is posted. Otherwise,
     * the method returns an error message which tells why the request body is invalid.
     * @see ReviewController#validateReview(Review)
     * @see ReviewWithoutUserId
     */
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

        String dataToSendToKafka = String.format("%s,%s,%.1f,%d", userId, requestBody.getProductId(),
                requestBody.getScore(), requestBody.getTimestamp().getTime());
        this.producer.sendMessage(dataToSendToKafka);

        return review;
    }

    /**
     * Validates the review that is posted from user in terms of 4 criteria:
     * <ul>
     * <li>User's id must contain alphanumeric characters.</li>
     * <li>Product's id must contain alphanumeric characters.<br></li>
     * <li>The score that the user give to the product must be in between 0 and 5.<br></li>
     * <li>The timestamp field must be non-negative.<br></li>
     * </ul>
     * @param review The user review that is to be validated.
     * @return If at least one of these criteria does not meet, the method returns a ValidationError. Otherwise, it
     * returns null.
     * @see ValidationError
     */
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
