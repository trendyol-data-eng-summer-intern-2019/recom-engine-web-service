package com.trendyol.recomengine.webservice.model;

import javax.validation.constraints.*;
import java.sql.Timestamp;

/**
 * To keep productId, score and timestamp without user id
 */
public class ReviewWithoutUserId {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "productId must only contain alphanumeric characters.")
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