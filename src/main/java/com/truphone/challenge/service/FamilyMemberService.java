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

import static com.truphone.challenge.util.UtilsService.attachEntity;
import static com.truphone.challenge.util.UtilsService.checkIdConsistency;


@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyRepository familyRepository;
    private final FamilyMemberMapper familyMemberMapper;
    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMember createFamilyMember(FamilyMemberDto familyMemberDto) {
        FamilyMember newFamilyMember = familyMemberMapper.toEntity(familyMemberDto);

        // Attach related entities
        attachEntity(familyMemberDto.getFamily(), familyRepository::getOne, newFamilyMember::setFamily);
        attachEntity(familyMemberDto.getFather(), familyMemberRepository::getOne, newFamilyMember::setFather);
        attachEntity(familyMemberDto.getMother(), familyMemberRepository::getOne, newFamilyMember::setMother);
        attachEntity(familyMemberDto.getSpouse(), familyMemberRepository::getOne, newFamilyMember::setSpouse);

        addSpouseRelationship(newFamilyMember);

        return familyMemberRepository.save(newFamilyMember);
    }

    public FamilyMember getFamilyMember(Long id) {
        return familyMemberRepository.findById(id).orElseThrow(FamilyMemberNotFoundException::new);
    }

    public List<FamilyMember> findAllByFamily(Long familyId) {
        Family family = familyRepository.findById(familyId).orElseThrow(FamilyNotFoundException::new);
        return familyMemberRepository.findAllByFamily(family);
    }

    public FamilyMember deleteFamilyMember(Long id) {
        FamilyMember familyMember = familyMemberRepository.findById(id).orElseThrow(FamilyMemberNotFoundException::new);
        removeSpouseRelationship(familyMember);

        familyMemberRepository.delete(familyMember);
        return familyMember;
    }

    public FamilyMember updateFamilyMember(Long id, FamilyMemberDto familyMemberDto) {
        checkIdConsistency(id, familyMemberDto);
        if (!familyMemberRepository.existsById(id)) {
            throw new FamilyMemberNotFoundException();
        }

        FamilyMember familyMember = familyMemberMapper.toEntity(familyMemberDto);
        familyMember = familyMemberRepository.save(familyMember);
        addSpouseRelationship(familyMember);

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

        if (partialUpdatedFamilyMember.getSpouse() != null) {
            removeSpouseRelationship(familyMember);
            familyMember.setSpouse(partialUpdatedFamilyMember.getSpouse());
            addSpouseRelationship(familyMember);
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

    /**
     * Add Spouse Relationship between the Entity
     */
    private void addSpouseRelationship(FamilyMember familyMember) {
        FamilyMember otherSpouse = familyMember.getSpouse();
        if (otherSpouse != null) {
            otherSpouse.setSpouse(familyMember);
            familyMemberRepository.save(otherSpouse);
        }
    }

    /**
     * Remove Spouse Relationship between the Entity
     */
    private void removeSpouseRelationship(FamilyMember familyMember) {
        FamilyMember otherSpouse = familyMember.getSpouse();
        if (otherSpouse != null) {
            otherSpouse.setSpouse(null);
            familyMemberRepository.save(otherSpouse);
        }
    }
}
