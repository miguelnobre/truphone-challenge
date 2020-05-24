package com.truphone.challenge.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper
public interface GenericMapper {

    @Named("percentageRepresentation")
    default BigDecimal buildPercentageRepresentation(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
