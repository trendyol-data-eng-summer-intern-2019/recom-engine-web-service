package com.trendyol.recomengine.webservice.repository;

import com.trendyol.recomengine.webservice.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Used to fetch recommendations of a specific user from MongoDB. Extends interface MongoRepository that is needed to
 * configure the Spring Boot application to retrieve data from MongoDB. Required confifgurations are made in the
 * application.yml file.
 */
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {

    /**
     * Searches for a document having the same _id with the method parameter in the specified collection of a specified
     * MongoDB database which are configured in the application.yml file.
     *
     * @param _id Document id to be searched.
     * @return If the document is found, converts document to a Recommendation object and returns it; returns null
     * otherwise.
     */
    Recommendation findBy_id(String _id);

}
