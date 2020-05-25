package com.truphone.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

import static com.truphone.challenge.util.Constants.LOCAL_DATE_PATTERN;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyPersonDto extends AbstractIdentifiableDto {
    private String firstName;
    private String middleName;
    private String lastName;
    @JsonFormat(pattern = LOCAL_DATE_PATTERN)
    private LocalDate dateOfBirth;
}