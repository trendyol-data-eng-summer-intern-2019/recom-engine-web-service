package com.trendyol.recomengine.webservice.controller;

import com.trendyol.recomengine.webservice.model.Recommendation;
import com.trendyol.recomengine.webservice.repository.RecommendationRepository;
import com.trendyol.recomengine.webservice.service.RecommendationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@ContextConfiguration(initializers = BaseMongoIntegrationTest.Initializer.class)
public class BaseMongoIntegrationTest {
    private static final int MONGO_PORT = 27017;

    private static GenericContainer mongoContainer = new GenericContainer("mongo:latest")
            .withExposedPorts(MONGO_PORT)
            .waitingFor(Wait.forListeningPort());
    @Autowired
    RecommendationRepository recommendationRepository;
    @Autowired
    RecommendationService recommendationService;

    @Test
    public void dummyTest() {
        String userId = "3";
        String[] recommendations = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        Recommendation recommendation = new Recommendation(userId, recommendations);
        recommendationRepository.save(recommendation);
        Assert.assertEquals(recommendationService.getRecommendations(userId).get_id(), userId);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            mongoContainer.start();

            TestPropertyValues.of("spring.data.mongodb.uri=mongodb://localhost:" + mongoContainer.getMappedPort(MONGO_PORT))
                    .applyTo(applicationContext);

            applicationContext.addApplicationListener(
                    applicationEvent -> {
                        if (applicationEvent instanceof ContextClosedEvent) {
                            mongoContainer.stop();
                        }
                    }
            );
        }
    }
}

