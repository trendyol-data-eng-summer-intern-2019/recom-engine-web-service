package com.trendyol.recomengine.controllers;

import com.trendyol.recomengine.engine.Producer;
import com.trendyol.recomengine.resource.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ReviewController {

    private final Producer producer;

    @Autowired
    ReviewController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/users/{userId}/reviews")
    public Object sendMessageToKafkaTopic(@RequestBody Review newReview) {
        if (newReview.getScore() > 5) return new Object() {
            public final String error = "Score can't be larger than 5";
        };

        String dataToSendToKafka = String.format("%s,%s,%.1f,%d", newReview.getUserId(), newReview.getProductId(), newReview.getScore(), newReview.getTime().getTime()/100);
        this.producer.sendMessage(dataToSendToKafka);

        return newReview;
    }
}
