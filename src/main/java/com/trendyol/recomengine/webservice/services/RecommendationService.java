package com.trendyol.recomengine.webservice.services;

import com.trendyol.recomengine.webservice.repositories.RecommendationRepository;
import com.trendyol.recomengine.webservice.resource.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
    @Autowired
    private RecommendationRepository repository;

    public Recommendation getRecommendations(String userId) {
        Recommendation fetched = repository.findBy_id(userId);
        return fetched == null ? generateDefaultRecommendations(userId) : fetched;
    }

    /**
     * Generates a default recommendation list for the user. For now, it returns 10 products' ids but default
     * recommendations could be generated based on a criterion.
     *
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
