package com.truphone.challenge.mapper;

import com.truphone.challenge.domain.FamilyMember;
import com.truphone.challenge.dto.FamilyMemberDto;
import com.truphone.challenge.dto.FamilyPersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface FamilyMemberMapper {

    @Mapping(source = "familyMember.firstName", target = "me.firstName")
    @Mapping(source = "familyMember.middleName", target = "me.middleName")
    @Mapping(source = "familyMember.lastName", target = "me.lastName")
    @Mapping(source = "familyMember.dateOfBirth", target = "me.dateOfBirth")
    FamilyMemberDto toDto(FamilyMember familyMember);

    List<FamilyMemberDto> toDto(List<FamilyMember> familyMember);

    // Needed to avoid circular mapping between FamilyMemberDto(s)
    FamilyPersonDto toFamilyPersonDto(FamilyMember familyMember);
}
