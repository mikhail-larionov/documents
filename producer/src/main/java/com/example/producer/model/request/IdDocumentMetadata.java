package com.example.producer.model.request;

import com.example.producer.model.enums.Gender;
import com.example.producer.model.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdDocumentMetadata extends DocumentMetadata {
    public IdDocumentMetadata(
            String userId,
            String name,
            String surname,
            String patronymic,
            String birthday,
            Gender gender,
            String passportNumber,
            String city,
            String livingAddress,
            LocalDate passportLastUpdateDate,
            TemplateType templateType
    ) {
        super(userId);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.gender = gender;
        this.passportNumber = passportNumber;
        this.city = city;
        this.livingAddress = livingAddress;
        this.passportLastUpdateDate = passportLastUpdateDate;
        this.templateType = templateType;
    }

    private String name;
    private String surname;
    private String patronymic;
    private String birthday;
    private Gender gender;
    private String passportNumber;
    private String city;
    private String livingAddress;
    private LocalDate passportLastUpdateDate;
    private TemplateType templateType;
}
