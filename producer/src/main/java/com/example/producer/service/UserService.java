package com.example.producer.service;

import com.example.producer.kafka.Sender;
import com.example.producer.model.request.UserCreateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Sender sender;

    public UserService(Sender sender) {
        this.sender = sender;
    }

    public void createUser(UserCreateRequest userCreateRequest) throws JsonProcessingException {
        sender.sendMessage(userCreateRequest);
    }
}
