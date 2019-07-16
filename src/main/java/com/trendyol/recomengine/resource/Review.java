package com.trendyol.recomengine.resource;

import java.util.Date;

public class Review {

    private final String userId;
    private final String productId;
    private final float score;
    private final Date time;

    public Review(String userId, String productId, float score, Date time) {
        this.userId = userId;
        this.productId = productId;
        this.score = score;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public float getScore() {
        return score;
    }

    public Date getTime() {
        return time;
    }

}