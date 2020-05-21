package com.truphone.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyMemberDto {
    private FamilyDto family;

    private FamilyPersonDto father;
    private FamilyPersonDto mother;
    private FamilyPersonDto spouse;

    @JsonUnwrapped
    private FamilyPersonDto me;
}
