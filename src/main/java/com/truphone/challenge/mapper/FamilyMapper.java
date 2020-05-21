package com.truphone.challenge.mapper;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.dto.FamilyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FamilyMapper {

    FamilyDto toDto(Family family);

    @Mapping(target = "id", ignore = true)
    Family toEntity(FamilyDto familyDto);
}
