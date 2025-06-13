package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HoldingEventProducer {

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendHoldingEvent(String key, String eventJson) {
        kafkaTemplate.send(topic, key, eventJson);
    }
}
