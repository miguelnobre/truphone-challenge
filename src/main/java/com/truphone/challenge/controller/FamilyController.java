package com.truphone.challenge.controller;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.mapper.FamilyMapper;
import com.truphone.challenge.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/families")
public class FamilyController {

    private final FamilyMapper familyMapper;
    private final FamilyService familyService;

    @PostMapping
    public ResponseEntity<FamilyDto> createFamily(@RequestBody FamilyDto familyDto) throws URISyntaxException {
        Family family = familyService.createFamily(familyDto);

        return ResponseEntity
                .created(new URI("api/families/" + family.getId()))
                .body(familyMapper.toDto(family));
    }
}
