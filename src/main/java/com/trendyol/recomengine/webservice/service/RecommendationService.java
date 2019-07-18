package com.trendyol.recomengine.webservice.service;

import com.trendyol.recomengine.webservice.model.Recommendation;
import org.springframework.stereotype.Service;

@Service
public interface RecommendationService {
    Recommendation getRecommendations(String userId);
}
