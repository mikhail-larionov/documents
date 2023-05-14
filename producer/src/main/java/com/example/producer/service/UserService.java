package com.example.producer.service;

import com.example.producer.kafka.Sender;
import com.example.producer.model.request.DocumentCreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Sender sender;

    public UserService(Sender sender) {
        this.sender = sender;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void createUser(DocumentCreateRequest userCreateRequest) throws JsonProcessingException {
        log.info("Sending message" + userCreateRequest);
        sender.sendMessage(userCreateRequest);
    }
}
