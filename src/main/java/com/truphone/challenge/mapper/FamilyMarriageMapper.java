package com.truphone.challenge.mapper;

import com.truphone.challenge.domain.FamilyMarriage;
import com.truphone.challenge.domain.FamilyMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FamilyMarriageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    FamilyMarriage toNewMarriage(FamilyMember familyMember, FamilyMember spouse);
}
