package com.trendyol.recomengine.resource;

import java.util.Date;

public class Review extends ReviewWithoutUserId {

    private final String userId;

    public Review(String userId, String productId, float score, Date time) {
        super(productId, score, time);
        this.userId = userId;
    }

    public Review(String userId, ReviewWithoutUserId reviewWithoutUserId) {
        super(reviewWithoutUserId.getProductId(), reviewWithoutUserId.getScore(), reviewWithoutUserId.getTimestamp());
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}