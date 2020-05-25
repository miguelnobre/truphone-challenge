package com.truphone.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDto<T> {
    private Integer offset;
    private Integer limit;
    private Boolean hasNext;
    private List<T> elements;
}
