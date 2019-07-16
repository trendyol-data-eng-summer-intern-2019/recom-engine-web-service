package com.trendyol.recomengine.webservice.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Used for sending messages to Kafka cluster. Needed for configuring the app as a Kafka producer. Can produce a message
 * to any desired topic. Required parameters like default topic are configured in the application.yml file.
 */
@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends message to the default topic configured in the application.yml file. If the message is successfully sent
     * to the Kafka cluster, logs the produced message at INFO level.
     * @param message Message string to be sent to Kafka cluster's default topic.
     */
    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.sendDefault(message);
    }
}
