package com.trendyol.recomengine.webservice.controllers;

import com.trendyol.recomengine.webservice.repositories.RecommendationRepository;
import com.trendyol.recomengine.webservice.resource.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles GET requests that contains that contain user id's. Fetches recommendations from database and returns them.
 */
@RestController
public class RecommendationController {

    @Autowired
    private RecommendationRepository repository;

    /**
     * This method is called when a GET request happens. Fetches recommendations from database for the user that is
     * specified in the argument.
     * @param userId The user's id who does the GET request.
     * @return If there is a recommendation list for the user in the database, returns it. Otherwise, returns a default
     * recommendation list using generateDefaultRecommendations.
     * @see RecommendationController#generateDefaultRecommendations(String)
     */
    @GetMapping("/users/{userId}/recommendations")
    Recommendation getRecommendations(@PathVariable String userId) {
        Recommendation fetched = repository.findBy_id(userId);
        return fetched == null ? generateDefaultRecommendations(userId) : fetched;
    }

    /**
     * Generates a default recommendation list for the user. For now, it returns 10 products' ids but default
     * recommendations could be generated based on a criterion.
     * @param userId The user id of the user that is to be recommended.
     * @return A list of recommendations that is generated for the user.
     * @see Recommendation
     */
    private Recommendation generateDefaultRecommendations(String userId) {
        return new Recommendation(userId, new String[]{
                "100", "101", "102", "103", "104",
                "105", "106", "107", "108", "109"
        });
    }
}
