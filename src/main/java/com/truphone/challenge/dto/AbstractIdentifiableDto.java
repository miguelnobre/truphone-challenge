package com.truphone.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractIdentifiableDto {
    private Long id;
}
