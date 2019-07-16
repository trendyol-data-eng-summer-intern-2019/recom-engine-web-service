package com.trendyol.recomengine.webservice.resource;

import java.util.Date;

public class ReviewWithoutUserId {

    private final String productId;
    private final float score;
    private final Date timestamp;

    ReviewWithoutUserId(String productId, float score, Date timestamp) {
        this.productId = productId;
        this.score = score;
        this.timestamp = timestamp;
    }

    public String getProductId() {
        return productId;
    }

    public float getScore() {
        return score;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}