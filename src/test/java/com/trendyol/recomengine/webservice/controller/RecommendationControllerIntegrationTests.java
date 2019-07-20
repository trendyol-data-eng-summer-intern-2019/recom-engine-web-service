package com.trendyol.recomengine.webservice.controller;

import com.trendyol.recomengine.webservice.model.Recommendation;
import com.trendyol.recomengine.webservice.repository.RecommendationRepository;
import com.trendyol.recomengine.webservice.service.RecommendationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testcontainers.containers.GenericContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class RecommendationControllerIntegrationTests {
    @Autowired
    RecommendationRepository recommendationRepository;

    @Autowired
    RecommendationService recommendationService;

    @Test
    public void contextLoad() {

    }

    @Test
    public void givenUserObject_whenSave_thenCreateNewUser() {
        String userId = "3";
        String[] recommendations = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        Recommendation recommendation = new Recommendation(userId, recommendations);
        recommendationRepository.save(recommendation);
        assert (recommendationService.getRecommendations(userId).get_id().equals(userId));
    }

}

