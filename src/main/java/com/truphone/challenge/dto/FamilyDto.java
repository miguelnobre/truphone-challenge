package com.truphone.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyDto extends AbstractIdentifiableDto {
    private String name;
    private String countryCode;
}
