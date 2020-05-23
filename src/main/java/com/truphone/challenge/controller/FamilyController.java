package com.truphone.challenge.controller;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.dto.UpdatePartiallyFamilyDto;
import com.truphone.challenge.exception.FamilyNotFoundException;
import com.truphone.challenge.mapper.FamilyMapper;
import com.truphone.challenge.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

//The transaction needs to start at the Controller level due to the lazy load that occurs during the mapping
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("api/families")
public class FamilyController {

    private final FamilyMapper familyMapper;
    private final FamilyService familyService;

    @PostMapping
    public ResponseEntity<FamilyDto> createFamily(@RequestBody FamilyDto familyDto) {
        Family family = familyService.createFamily(familyDto);

        return ResponseEntity
                .created(URI.create("api/families/" + family.getId()))
                .body(familyMapper.toDto(family));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamilyDto> getFamily(@PathVariable Long id) {
        Family family = familyService.getFamily(id).orElseThrow(FamilyNotFoundException::new);

        return ResponseEntity
                .ok(familyMapper.toDto(family));
    }

    @GetMapping
    public ResponseEntity<List<FamilyDto>> getAllFamilies() {
        List<Family> families = familyService.getAllFamilies();

        return ResponseEntity
                .ok(familyMapper.toDto(families));
    }

    @GetMapping("/country/{isoCountryCode}")
    public ResponseEntity<List<FamilyDto>> getFamiliesByCountryCode(@PathVariable String isoCountryCode) {
        List<Family> familiesByCountryCode = familyService.getFamiliesByCountryCode(isoCountryCode);

        return ResponseEntity
                .ok(familyMapper.toDto(familiesByCountryCode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FamilyDto> deleteFamily(@PathVariable Long id) {
        Family deletedFmily = familyService.deleteFamily(id);

        return ResponseEntity
                .ok(familyMapper.toDto(deletedFmily));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyDto> updateFamily(@PathVariable Long id, @RequestBody FamilyDto familyDto) {
        Family family = familyService.updateFamily(id, familyDto);

        return ResponseEntity
                .ok(familyMapper.toDto(family));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FamilyDto> partialUpdateFamily(@PathVariable Long id, @RequestBody UpdatePartiallyFamilyDto familyDto) {
        Family family = familyService.partialUpdateFamily(id, familyDto);

        return ResponseEntity
                .ok(familyMapper.toDto(family));
    }
}
