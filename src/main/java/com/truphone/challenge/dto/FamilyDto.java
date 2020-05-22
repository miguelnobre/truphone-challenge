package com.truphone.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyDto extends AbstractIdentifiableDto{
    private String name;
    @Length(min = 3, max = 3)
    private String countryCode;
}
