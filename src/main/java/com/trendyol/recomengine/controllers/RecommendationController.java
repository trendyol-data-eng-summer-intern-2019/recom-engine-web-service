package com.trendyol.recomengine.controllers;

import com.trendyol.recomengine.repositories.RecommendationRepository;
import com.trendyol.recomengine.resource.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecommendationController {

    @Autowired
    private RecommendationRepository repository;

    private String[] defaultRecoms = {"100", "101", "102", "103", "104", "105", "106", "107", "108", "109"};

    @GetMapping("/users/{_id}/recommendations")
    Object recommend(@PathVariable String _id) {

        Recommendation fetched = repository.findBy_id(_id);
        return fetched == null ? new Recommendation(_id, defaultRecoms) : fetched;

    }

    /*@PostMapping("/users/{_id}/recommendations")
    public Object dummy(@PathVariable String _id) {
        String[] test = {"3","4"};
        repository.save(new Recommendation("6", test));
        repository.save(new Recommendation("7", test));
        repository.save(new Recommendation("9", test));

        return "success";
    }*/

}
