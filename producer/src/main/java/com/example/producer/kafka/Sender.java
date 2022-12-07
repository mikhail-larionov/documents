package com.example.producer.kafka;

import com.example.producer.model.request.UserCreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Value(value = "${kafka.topic}")
    private String topicName;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Sender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserCreateRequest user) throws JsonProcessingException {
        String userCreateRequest = objectMapper.writeValueAsString(user);
        kafkaTemplate.send(topicName, userCreateRequest);
    }
}
