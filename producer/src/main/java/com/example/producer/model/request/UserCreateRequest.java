package com.example.producer.model.request;

import com.example.producer.model.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserCreateRequest {
    private String id;
    private String name;
    private String surname;
    private String patronymic;
    private String birthday;
    private Gender gender;
    private String passportNumber;
    private String city;
    private String livingAddress;
    private LocalDate passportLastUpdateDate;
}
