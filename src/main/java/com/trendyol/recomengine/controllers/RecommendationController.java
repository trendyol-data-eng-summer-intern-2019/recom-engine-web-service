package com.trendyol.recomengine.controllers;

import com.trendyol.recomengine.repositories.RecommendationRepository;
import com.trendyol.recomengine.resource.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {

    @Autowired
    private RecommendationRepository repository;

    @GetMapping("/users/{userId}/recommendations")
    Recommendation getRecommendations(@PathVariable String userId) {
        Recommendation fetched = repository.findBy_id(userId);
        return fetched == null ? generateDefaultRecommendations(userId) : fetched;
    }

    private Recommendation generateDefaultRecommendations(String userId) {
        return new Recommendation(userId, new String[]{"100", "101", "102", "103", "104", "105", "106", "107", "108", "109"});
    }
}
