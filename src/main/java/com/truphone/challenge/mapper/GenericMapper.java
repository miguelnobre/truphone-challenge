package com.truphone.challenge.mapper;

import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper
public interface GenericMapper {

    default BigDecimal buildBigDecimal(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
