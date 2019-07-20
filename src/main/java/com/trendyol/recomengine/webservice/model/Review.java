package com.trendyol.recomengine.webservice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

/**
 * To keep reviews
 */
public class Review extends ReviewWithoutUserId {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "userId must only contain alphanumeric characters.")
    private final String userId;

    /**
     * @param userId    User's id who reviewed
     * @param productId Reviewed product's id
     * @param score     Reviewed score
     * @param time      Review timestamp
     */
    public Review(String userId, String productId, float score, Timestamp time) {
        super(productId, score, time);
        this.userId = userId;
    }

    /**
     * @param userId              User's id who reviewed
     * @param reviewWithoutUserId ProductId, score and timestamp without user id
     */
    public Review(String userId, ReviewWithoutUserId reviewWithoutUserId) {
        super(reviewWithoutUserId.getProductId(), reviewWithoutUserId.getScore(), reviewWithoutUserId.getTimestamp());
        this.userId = userId;
    }

    /**
     * @return The user id who reviewed
     */
    public String getUserId() {
        return userId;
    }

}
