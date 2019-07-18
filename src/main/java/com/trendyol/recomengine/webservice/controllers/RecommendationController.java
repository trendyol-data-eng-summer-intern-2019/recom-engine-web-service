package com.trendyol.recomengine.webservice.controllers;

import com.trendyol.recomengine.webservice.repositories.RecommendationRepository;
import com.trendyol.recomengine.webservice.resource.Recommendation;
import com.trendyol.recomengine.webservice.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles GET requests that contains that contain user id's. Fetches recommendations from database and returns them.
 */
@RestController
class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    /**
     * This method is called when a GET request happens. Fetches recommendations from database for the user that is
     * specified in the argument.
     *
     * @param userId The user's id who sends the GET request.
     * @return If there is a recommendation list for the user in the database, returns it. Otherwise, returns a default
     * recommendation list using generateDefaultRecommendations.
     * @see RecommendationController#generateDefaultRecommendations(String)
     */
    @GetMapping("/users/{userId}/recommendations")
    Recommendation getRecommendation(@PathVariable String userId) {
        return recommendationService.getRecommendations(userId);
    }
}
