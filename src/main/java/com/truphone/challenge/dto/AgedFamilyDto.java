package com.truphone.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgedFamilyDto extends FamilyDto {
    private int averageAge;
}
