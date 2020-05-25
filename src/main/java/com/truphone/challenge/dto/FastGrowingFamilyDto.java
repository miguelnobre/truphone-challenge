package com.truphone.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FastGrowingFamilyDto extends FamilyDto {

    private BigDecimal growingRate;
}
