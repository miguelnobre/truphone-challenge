package com.truphone.challenge.service;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.domain.view.AgedFamilyView;
import com.truphone.challenge.dto.AgedFamilyDto;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.exception.FamilyNotFoundException;
import com.truphone.challenge.mapper.FamilyMapper;
import com.truphone.challenge.repository.AgedFamilyRepository;
import com.truphone.challenge.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static com.truphone.challenge.util.UtilsService.checkIdConsistency;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyMapper familyMapper;
    private final FamilyRepository familyRepository;
    private final AgedFamilyRepository agedFamilyRepository;

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

    public Family partialUpdateFamily(Long id, FamilyDto familyDto) {
        checkIdConsistency(id, familyDto);

        Family family = familyRepository.findById(familyDto.getId()).orElseThrow(FamilyNotFoundException::new);

        if (familyDto.getName() != null) {
            family.setName(familyDto.getName());
        }

        if (familyDto.getCountryCode() != null) {
            family.setCountryCode(familyDto.getCountryCode());
        }

        return familyRepository.save(family);
    }

    public AgedFamilyDto findAgedFamily() {
        AgedFamilyView agedFamily = agedFamilyRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        // Aged Family is NULL when there aren't Family Members stored,
        // otherwise always returns one element
        if (agedFamily == null) {
            return null;
        }

        LocalDate averageEpochAsDate = LocalDate.ofInstant(Instant.ofEpochSecond(agedFamily.getAvgAgeAsEpoch().longValue()), ZoneId.systemDefault());
        int averageAge = LocalDate.now().getYear() - averageEpochAsDate.getYear();

        Family family = familyRepository.getOne(agedFamily.getFamilyId());
        AgedFamilyDto agedFamilyDto = familyMapper.toDto(family, averageAge);

        return agedFamilyDto;
    }
}
