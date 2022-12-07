package com.example.producer.model.request;

import com.example.producer.model.enums.Sex;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserCreateRequest {
    private String id = null;
    private String name;
    private String surname;
    private String birthday = LocalDate.now().toString();
    private Sex sex;

}
