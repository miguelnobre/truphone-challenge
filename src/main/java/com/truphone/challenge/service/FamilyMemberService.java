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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.truphone.challenge.util.UtilsService.attachEntity;
import static com.truphone.challenge.util.UtilsService.checkIdConsistency;


@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyRepository familyRepository;
    private final FamilyMemberMapper familyMemberMapper;
    private final FamilyMarriageService familyMarriageService;
    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMember createFamilyMember(FamilyMemberDto familyMemberDto) {
        FamilyMember newFamilyMember = familyMemberMapper.toEntity(familyMemberDto);
        familyMemberRepository.save(newFamilyMember);

        if (familyMemberDto.getSpouse() != null) {
            familyMarriageService.buildSpouseRelationShip(newFamilyMember, familyMemberDto.getSpouse().getId());
        }

        // Attach related entities
        attachEntity(familyMemberDto.getFamily(), familyRepository::getOne, newFamilyMember::setFamily);
        attachEntity(familyMemberDto.getFather(), familyMemberRepository::getOne, newFamilyMember::setFather);
        attachEntity(familyMemberDto.getMother(), familyMemberRepository::getOne, newFamilyMember::setMother);

        return newFamilyMember;
    }

    public FamilyMember getFamilyMember(Long id) {
        return familyMemberRepository.findById(id).orElseThrow(FamilyMemberNotFoundException::new);
    }

    public Page<FamilyMember> findAllByFamily(Long familyId, Pageable page) {
        Family family = familyRepository.findById(familyId).orElseThrow(FamilyNotFoundException::new);
        return familyMemberRepository.findAllByFamily(family, page);
    }

    public FamilyMember deleteFamilyMember(Long id) {
        FamilyMember familyMember = familyMemberRepository.findById(id).orElseThrow(FamilyMemberNotFoundException::new);

        familyMarriageService.destroyMarriage(familyMember);
        familyMemberRepository.delete(familyMember);

        return familyMember;
    }

    public FamilyMember updateFamilyMember(Long id, FamilyMemberDto familyMemberDto) {
        checkIdConsistency(id, familyMemberDto);
        FamilyMember familyMember = familyMemberRepository.findById(id).orElseThrow(FamilyMemberNotFoundException::new);
        familyMarriageService.destroyMarriage(familyMember);

        familyMember = familyMemberRepository.save(familyMemberMapper.toEntity(familyMemberDto));
        if (familyMemberDto.getSpouse() != null) {
            familyMarriageService.buildSpouseRelationShip(familyMember, familyMemberDto.getSpouse().getId());
        }

        return familyMember;
    }

    public FamilyMember partialUpdateFamily(Long id, FamilyMemberDto familyMemberDto) {
        checkIdConsistency(id, familyMemberDto);

        FamilyMember familyMember = familyMemberRepository.findById(familyMemberDto.getId()).orElseThrow(FamilyMemberNotFoundException::new);
        FamilyMember partialUpdatedFamilyMember = familyMemberMapper.toEntity(familyMemberDto);

        if (partialUpdatedFamilyMember.getFamily() != null) {
            familyMember.setFamily(partialUpdatedFamilyMember.getFamily());
        }

        if (partialUpdatedFamilyMember.getFather() != null) {
            familyMember.setFather(partialUpdatedFamilyMember.getFather());
        }

        if (partialUpdatedFamilyMember.getMother() != null) {
            familyMember.setMother(partialUpdatedFamilyMember.getMother());
        }

        if (familyMemberDto.getSpouse() != null) {
            familyMarriageService.destroyMarriage(familyMember);
            familyMarriageService.buildSpouseRelationShip(familyMember, familyMemberDto.getSpouse().getId());
        }

        if (partialUpdatedFamilyMember.getFirstName() != null) {
            familyMember.setFirstName(partialUpdatedFamilyMember.getFirstName());
        }

        if (partialUpdatedFamilyMember.getMiddleName() != null) {
            familyMember.setMiddleName(partialUpdatedFamilyMember.getMiddleName());
        }

        if (partialUpdatedFamilyMember.getLastName() != null) {
            familyMember.setLastName(partialUpdatedFamilyMember.getLastName());
        }

        if (partialUpdatedFamilyMember.getDateOfBirth() != null) {
            familyMember.setDateOfBirth(partialUpdatedFamilyMember.getDateOfBirth());
        }

        return familyMemberRepository.save(familyMember);
    }

    public FamilyMember findSpouse(FamilyMember familyMember) {
        if (familyMember.getFamilyMarriage() == null) {
            return null;
        }
        return familyMemberRepository.findByFamilyMarriageAndIdNot(familyMember.getFamilyMarriage(), familyMember.getId());
    }
}
