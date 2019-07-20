package com.trendyol.recomengine.webservice.controller;

import org.junit.runner.RunWith;
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
public abstract class BaseMongoIntegrationTest {
    private static final int MONGO_PORT = 27017;

    private static GenericContainer mongoContainer = new GenericContainer("mongo:latest")
            .withExposedPorts(MONGO_PORT)
            .waitingFor(Wait.forListeningPort());

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

