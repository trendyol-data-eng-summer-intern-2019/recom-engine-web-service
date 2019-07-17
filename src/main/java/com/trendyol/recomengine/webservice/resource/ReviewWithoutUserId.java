package com.trendyol.recomengine.webservice.resource;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * To keep productId, score and timestamp without user id
 */
public class ReviewWithoutUserId {

    @NotBlank
    private final String productId;

    @DecimalMin(value = "0.5", inclusive = true) @Max(5)
    private final float score;

    private final Date timestamp;

    /**
     * @param productId Reviewed product's id
     * @param score Reviewed score
     * @param timestamp Review timestamp
     */
    ReviewWithoutUserId(String productId, float score, Date timestamp) {
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
    public Date getTimestamp() {
        return timestamp;
    }

}