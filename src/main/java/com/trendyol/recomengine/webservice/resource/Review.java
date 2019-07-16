package com.trendyol.recomengine.webservice.resource;

import java.util.Date;

/**
 * To keep reviews
 */
public class Review extends ReviewWithoutUserId {

    private final String userId;

    /**
     *
     * @param userId User's id who reviewed
     * @param productId Reviewed product's id
     * @param score Reviewed score
     * @param time Review timestamp
     */
    public Review(String userId, String productId, float score, Date time) {
        super(productId, score, time);
        this.userId = userId;
    }

    /**
     *
     * @param userId User's id who reviewed
     * @param reviewWithoutUserId ProductId, score and timestamp without user id
     */
    public Review(String userId, ReviewWithoutUserId reviewWithoutUserId) {
        super(reviewWithoutUserId.getProductId(), reviewWithoutUserId.getScore(), reviewWithoutUserId.getTimestamp());
        this.userId = userId;
    }

    /**
     *
     * @return The user id who reviewed
     */
    public String getUserId() {
        return userId;
    }

}