package com.trendyol.recomengine.webservice.repositories;

import com.trendyol.recomengine.webservice.resource.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationRepository extends MongoRepository<Recommendation, String> {

    Recommendation findBy_id(String _id);

}