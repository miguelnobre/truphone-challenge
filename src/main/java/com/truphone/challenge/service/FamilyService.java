package com.truphone.challenge.service;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.domain.view.AgedFamilyView;
import com.truphone.challenge.domain.view.FastGrowingFamilyView;
import com.truphone.challenge.dto.AgedFamilyDto;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.dto.FastGrowingFamilyDto;
import com.truphone.challenge.exception.FamilyNotFoundException;
import com.truphone.challenge.mapper.FamilyMapper;
import com.truphone.challenge.repository.AgedFamilyRepository;
import com.truphone.challenge.repository.FamilyRepository;
import com.truphone.challenge.repository.FastGrowingFamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.truphone.challenge.util.UtilsService.checkIdConsistency;
import static com.truphone.challenge.util.UtilsService.checkIsoCountryCodeIsValid;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyMapper familyMapper;
    private final FamilyRepository familyRepository;
    private final AgedFamilyRepository agedFamilyRepository;
    private final FastGrowingFamilyRepository fastGrowingFamilyRepository;

    public Family createFamily(FamilyDto familyDto) {
        checkIsoCountryCodeIsValid(familyDto.getCountryCode());
        return familyRepository.save(familyMapper.toEntity(familyDto));
    }

    public Optional<Family> getFamily(Long id) {
        return familyRepository.findById(id);
    }

    public Page<Family> getAllFamilies(Pageable page) {
        return familyRepository.findAll(page);
    }

    public Page<Family> getFamiliesByCountryCode(String isoCountryCode, Pageable page) {
        return familyRepository.findAllByCountryCode(isoCountryCode, page);
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
        checkIsoCountryCodeIsValid(familyDto.getCountryCode());

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
            checkIsoCountryCodeIsValid(familyDto.getCountryCode());
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

        Family family = familyRepository.getOne(agedFamily.getFamilyId());
        AgedFamilyDto agedFamilyDto = familyMapper.toDto(family, agedFamily);

        return agedFamilyDto;
    }

    public FastGrowingFamilyDto findFastGrowingFamily() {
        FastGrowingFamilyView fastGrowingFamily = fastGrowingFamilyRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        // Fastest Growing Family is NULL when there aren't Family Members stored,
        // otherwise always returns one element
        if (fastGrowingFamily == null) {
            return null;
        }

        Family family = familyRepository.getOne(fastGrowingFamily.getFamilyId());
        FastGrowingFamilyDto fastGrowingFamilyDto = familyMapper.toDto(family, fastGrowingFamily.getGrowingRate());

        return fastGrowingFamilyDto;
    }
}
