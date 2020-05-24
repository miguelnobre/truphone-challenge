package com.truphone.challenge.mapper;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.dto.AgedFamilyDto;
import com.truphone.challenge.dto.FamilyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface FamilyMapper {

    FamilyDto toDto(Family family);

    List<FamilyDto> toDto(List<Family> family);

    @Mapping(target = "id", ignore = true)
    Family toEntity(FamilyDto familyDto);

    AgedFamilyDto toDto(Family agedFamily, int averageAge);
}
