package com.truphone.challenge.service;

import com.truphone.challenge.domain.FamilyMarriage;
import com.truphone.challenge.domain.FamilyMember;
import com.truphone.challenge.exception.SpouseAlreadyMarriedException;
import com.truphone.challenge.exception.SpouseNotFoundException;
import com.truphone.challenge.mapper.FamilyMarriageMapper;
import com.truphone.challenge.repository.FamilyMarriageRepository;
import com.truphone.challenge.repository.FamilyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyMarriageService {

    private final FamilyMarriageMapper familyMarriageMapper;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyMarriageRepository familyMarriageRepository;

    public FamilyMarriage buildSpouseRelationShip(FamilyMember familyMember, Long spouseId) {
        FamilyMember spouse = familyMemberRepository.findById(spouseId).orElseThrow(SpouseNotFoundException::new);
        if (spouse.getFamilyMarriage() != null) {
            throw new SpouseAlreadyMarriedException();
        }

        FamilyMarriage marriage = familyMarriageMapper.toNewMarriage(familyMember, spouse);
        familyMarriageRepository.save(marriage);

        familyMember.setFamilyMarriage(marriage);
        spouse.setFamilyMarriage(marriage);

        familyMemberRepository.saveAll(List.of(familyMember, spouse));
        return marriage;
    }

    public void destroyMarriage(FamilyMember familyMember) {
        if (familyMember.getFamilyMarriage() == null) {
            return;
        }

        FamilyMarriage familyMarriage = familyMember.getFamilyMarriage();

        // Break the Marriage Relationship
        familyMember.setFamilyMarriage(null);
        FamilyMember spouse = familyMemberRepository.findByFamilyMarriageAndIdNot(familyMarriage, familyMember.getId());
        spouse.setFamilyMarriage(null);

        familyMemberRepository.saveAll(List.of(familyMember, spouse));

        // Delete Marriage
        familyMarriageRepository.delete(familyMarriage);
    }
}
