package com.truphone.challenge.service;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.dto.UpdatePartiallyFamilyDto;
import com.truphone.challenge.exception.FamilyNotFoundException;
import com.truphone.challenge.mapper.FamilyMapper;
import com.truphone.challenge.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.truphone.challenge.util.UtilsService.checkIdConsistency;

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

    public List<Family> getFamiliesByCountryCode(String isoCountryCode) {
        return familyRepository.findAllByCountryCode(isoCountryCode);
    }

    public Family deleteFamily(Long id) {
        Family family = familyRepository.findById(id).orElseThrow(FamilyNotFoundException::new);
        if (!family.getFamilyMemberList().isEmpty()) {
            throw new IllegalArgumentException("Family cannot have related Family Members!");
        }

        familyRepository.delete(family);
        return family;
    }

    public Family updateFamily(Long id, FamilyDto familyDto) {
        checkIdConsistency(id, familyDto);

        Family family = familyRepository.findById(familyDto.getId()).orElseThrow(FamilyNotFoundException::new);
        family.setName(familyDto.getName());
        family.setCountryCode(familyDto.getCountryCode());

        return familyRepository.save(family);
    }

    public Family partialUpdateFamily(Long id, UpdatePartiallyFamilyDto familyDto) {
        checkIdConsistency(id, familyDto);

        Family family = familyRepository.findById(familyDto.getId()).orElseThrow(FamilyNotFoundException::new);

        if (familyDto.getName() != null || familyDto.isNullAsDeleted()) {
            family.setName(familyDto.getName());
        }

        if (familyDto.getCountryCode() != null || familyDto.isNullAsDeleted()) {
            family.setCountryCode(familyDto.getCountryCode());
        }

        return familyRepository.save(family);
    }
}
