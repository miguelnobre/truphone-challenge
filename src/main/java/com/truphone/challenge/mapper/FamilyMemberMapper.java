package com.truphone.challenge.mapper;

import com.truphone.challenge.domain.FamilyMember;
import com.truphone.challenge.dto.FamilyMemberDto;
import com.truphone.challenge.dto.FamilyPersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface FamilyMemberMapper {

    // Needed to avoid circular mapping between FamilyMemberDto(s)
    FamilyPersonDto toFamilyPersonDto(FamilyMember familyMember);

    @Mapping(source = "firstName", target = "me.firstName")
    @Mapping(source = "middleName", target = "me.middleName")
    @Mapping(source = "lastName", target = "me.lastName")
    @Mapping(source = "dateOfBirth", target = "me.dateOfBirth")
    FamilyMemberDto toDto(FamilyMember familyMember);

    @Mapping(source = "familyMember.firstName", target = "me.firstName")
    @Mapping(source = "familyMember.middleName", target = "me.middleName")
    @Mapping(source = "familyMember.lastName", target = "me.lastName")
    @Mapping(source = "familyMember.dateOfBirth", target = "me.dateOfBirth")
    @Mapping(source = "familyMember.id", target = "id")
    @Mapping(source = "familyMember.family", target = "family")
    @Mapping(source = "familyMember.father", target = "father")
    @Mapping(source = "familyMember.mother", target = "mother")
    FamilyMemberDto toDtoWithSpouse(FamilyMember familyMember, FamilyMember spouse);

    List<FamilyMemberDto> toDto(List<FamilyMember> familyMember);

    @Mapping(source = "me.firstName", target = "firstName")
    @Mapping(source = "me.middleName", target = "middleName")
    @Mapping(source = "me.lastName", target = "lastName")
    @Mapping(source = "me.dateOfBirth", target = "dateOfBirth")
    FamilyMember toEntity(FamilyMemberDto familyMemberDto);
}
