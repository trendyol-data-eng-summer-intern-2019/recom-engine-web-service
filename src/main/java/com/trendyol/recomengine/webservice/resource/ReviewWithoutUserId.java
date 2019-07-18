package com.trendyol.recomengine.webservice.resource;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

/**
 * To keep productId, score and timestamp without user id
 */
public class ReviewWithoutUserId {

    @NotBlank
    private final String productId;

    @DecimalMin(value = "0.5")
    @Max(5)
    private final float score;

    @NotNull
    private final Timestamp timestamp;

    /**
     * @param productId Reviewed product's id
     * @param score     Reviewed score
     * @param timestamp Review timestamp
     */
    ReviewWithoutUserId(String productId, float score, Timestamp timestamp) {
        this.productId = productId;
        this.score = score;
        this.timestamp = timestamp;
    }

    /**
     * @return Id of the product
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @return Review score of the product
     */
    public float getScore() {
        return score;
    }

    /**
     * @return Review timestamp in seconds
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

}