package com.trendyol.recomengine.webservice.resource;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * To keep user ids and their specific recommendations
 */
@Document(collection = "recommendations")
public class Recommendation {

    /**
     * To keep id of the user
     */
    @Id
    private final String _id;

    /**
     * To keep specific recommendations for each user in an array
     */
    private final String[] recommendations;

    /**
     *
     * @param _id The user's id who sends the POST request.
     * @param recommendations Specific recommendations for the user
     */
    public Recommendation(String _id, String[] recommendations) {
        this._id = _id;
        this.recommendations = recommendations;
    }

    /**
     *
     * @return Id of the user
     */
    public String get_id() {
        return _id;
    }

    /**
     *
     * @return Recommendations of the user
     */
    public String[] getRecommendations() {
        return recommendations;
    }
}
