package com.trendyol.recomengine.webservice.controller;

import com.trendyol.recomengine.webservice.model.Recommendation;
import com.trendyol.recomengine.webservice.repository.RecommendationRepository;
import com.trendyol.recomengine.webservice.service.RecommendationService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RecommendationControllerIntegrationTests extends BaseMongoIntegrationTest {
    @Autowired
    private RecommendationRepository recommendationRepository;
    @Autowired
    private RecommendationService recommendationService;

    @Test
    public void getRecommendationShouldReturnTheRelevantUsersRecommendation() {
        String userId = "3";
        String[] recommendations = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        Recommendation recommendation = new Recommendation(userId, recommendations);
        recommendationRepository.save(recommendation);
        Assert.assertEquals(recommendationService.getRecommendations(userId).get_id(), userId);
    }
}
