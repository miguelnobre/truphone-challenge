package com.truphone.challenge.service;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.mapper.FamilyMapper;
import com.truphone.challenge.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyMapper familyMapper;
    private final FamilyRepository familyRepository;

    public Family createFamily(FamilyDto familyDto) {
        return familyRepository.save(familyMapper.toEntity(familyDto));
    }
}
