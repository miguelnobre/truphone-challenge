package com.truphone.challenge.service;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.mapper.FamilyMapper;
import com.truphone.challenge.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyMapper familyMapper;
    private final FamilyRepository familyRepository;

    public Family createFamily(FamilyDto familyDto) {
        return familyRepository.save(familyMapper.toEntity(familyDto));
    }

    public Optional<Family> getFamily(Long id) {
        return familyRepository.findById(id);
    }

    public List<Family> getAllFamilies() {
        return familyRepository.findAll();
    }

    public void deleteFamily(Family family) {
        familyRepository.delete(family);
    }
}
