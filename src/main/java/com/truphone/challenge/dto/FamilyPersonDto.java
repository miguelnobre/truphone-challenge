package com.truphone.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyPersonDto extends AbstractIdentifiableDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
}