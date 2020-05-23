package com.truphone.challenge.service;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.domain.FamilyMember;
import com.truphone.challenge.dto.FamilyMemberDto;
import com.truphone.challenge.exception.FamilyMemberNotFoundException;
import com.truphone.challenge.exception.FamilyNotFoundException;
import com.truphone.challenge.mapper.FamilyMemberMapper;
import com.truphone.challenge.repository.FamilyMemberRepository;
import com.truphone.challenge.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.truphone.challenge.util.UtilsService.enrichWithEntity;


@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyRepository familyRepository;
    private final FamilyMemberMapper familyMemberMapper;
    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMember createFamilyMember(FamilyMemberDto familyMemberDto) {
        FamilyMember newFamilyMember = familyMemberMapper.toEntity(familyMemberDto);

        enrichWithEntity(familyMemberDto.getFamily(), familyRepository::getOne, newFamilyMember::setFamily);
        enrichWithEntity(familyMemberDto.getFather(), familyMemberRepository::getOne, newFamilyMember::setFather);
        enrichWithEntity(familyMemberDto.getMother(), familyMemberRepository::getOne, newFamilyMember::setMother);
        enrichWithEntity(familyMemberDto.getSpouse(), familyMemberRepository::getOne, newFamilyMember::setSpouse);

        newFamilyMember = familyMemberRepository.save(newFamilyMember);

        // Reflect the Spouse in the other person (Monogamous family!)
        FamilyMember newFamilyMemberSpouse = newFamilyMember.getSpouse();
        if (newFamilyMemberSpouse != null) {
            newFamilyMemberSpouse.setSpouse(newFamilyMember);
            familyMemberRepository.save(newFamilyMemberSpouse);
        }

        return newFamilyMember;
    }

    public FamilyMember getFamilyMember(Long id) {
        return familyMemberRepository.findById(id).orElseThrow(FamilyMemberNotFoundException::new);
    }

    public List<FamilyMember> findAllByFamily(Long familyId) {
        Family family = familyRepository.findById(familyId).orElseThrow(FamilyNotFoundException::new);
        return familyMemberRepository.findAllByFamily(family);
    }
}
