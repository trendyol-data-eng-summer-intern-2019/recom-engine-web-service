package com.trendyol.recomengine.webservice.services;

import com.trendyol.recomengine.webservice.repositories.RecommendationRepository;
import com.trendyol.recomengine.webservice.resource.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface RecommendationService {
    Recommendation getRecommendations(String userId);
}
