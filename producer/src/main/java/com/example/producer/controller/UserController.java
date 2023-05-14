package com.example.producer.controller;

import com.example.producer.model.enums.Gender;
import com.example.producer.model.enums.TemplateType;
import com.example.producer.model.request.DocumentCreateRequest;
import com.example.producer.model.request.IdDocumentMetadata;
import com.example.producer.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class UserController {

    private final UserService userService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10000);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser() throws JsonProcessingException {
        for (int j = 0; j < 10000; j++) {
            executorService.execute(
                    () -> {
                        for (int i = 0; i < 3; i++) {
                            System.out.println(i);
                            DocumentCreateRequest documentCreateRequest = new DocumentCreateRequest(
                                    TemplateType.ID,
                                    new IdDocumentMetadata(
                                            UUID.randomUUID().toString(),
                                            "name",
                                            "surname",
                                            "patronymic",
                                            "birthday",
                                            Gender.MALE,
                                            "passportNumber",
                                            "city",
                                            "livingAddress",
                                            LocalDate.now(),
                                            TemplateType.ID
                                    )
                            );
                            try {
                                userService.createUser(documentCreateRequest);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );
//            userService.createUser(documentCreateRequest);
//        }
        }
    }
}
