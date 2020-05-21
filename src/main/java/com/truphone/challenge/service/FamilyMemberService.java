package com.truphone.challenge.service;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.domain.FamilyMember;
import com.truphone.challenge.exception.FamilyNotFoundException;
import com.truphone.challenge.repository.FamilyMemberRepository;
import com.truphone.challenge.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;

    public List<FamilyMember> findAllByFamily(Long familyId) {
        Family family = familyRepository.findById(familyId).orElseThrow(FamilyNotFoundException::new);
        return familyMemberRepository.findAllByFamily(family);
    }
}
