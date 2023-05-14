package com.example.producer.kafka;

import com.example.producer.model.request.DocumentCreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Value(value = "${kafka.topic}")
    private String topicName;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Sender(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(DocumentCreateRequest user) throws JsonProcessingException {
        String userCreateRequest = objectMapper.writeValueAsString(user);
        kafkaTemplate.send(topicName, userCreateRequest);
    }
}
