package com.truphone.challenge.mapper;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.dto.AgedFamilyDto;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.dto.FastGrowingFamilyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(uses = GenericMapper.class)
public interface FamilyMapper {

    FamilyDto toDto(Family family);

    List<FamilyDto> toDto(List<Family> family);

    @Mapping(target = "id", ignore = true)
    Family toEntity(FamilyDto familyDto);

    AgedFamilyDto toDto(Family agedFamily, int averageAge);

    @Mapping(source = "growingRate", target = "growingRate", qualifiedByName = "percentageRepresentation")
    FastGrowingFamilyDto toDto(Family agedFamily, BigDecimal growingRate);
}
