package com.trendyol.recomengine.resource;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recommendations")
public class Recommendation {

    @Id
    private final String _id;

    private final String [] recommendations;

    public Recommendation(String _id, String [] recommendations) {
        this._id = _id;
        this.recommendations = recommendations;
    }

    public String get_id() {
        return _id;
    }

    public String[] getRecommendations() {
        return recommendations;
    }
}

