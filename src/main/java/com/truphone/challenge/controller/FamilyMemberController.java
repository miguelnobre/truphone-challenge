package com.truphone.challenge.controller;

import com.truphone.challenge.domain.FamilyMember;
import com.truphone.challenge.dto.FamilyMemberDto;
import com.truphone.challenge.mapper.FamilyMemberMapper;
import com.truphone.challenge.service.FamilyMemberService;
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

@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("api/family-members")
public class FamilyMemberController {

    private final FamilyMemberMapper familyMemberMapper;
    private final FamilyMemberService familyMemberService;

    @PostMapping
    public ResponseEntity<FamilyMemberDto> createFamilyMember(@RequestBody FamilyMemberDto familyMemberDto) {
        FamilyMember familyMember = familyMemberService.createFamilyMember(familyMemberDto);
        FamilyMember spouse = familyMemberService.findSpouse(familyMember);

        return ResponseEntity
                .created(URI.create("api/family-members/" + familyMember.getId()))
                .body(familyMemberMapper.toDtoWithSpouse(familyMember, spouse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamilyMemberDto> getFamilyMember(@PathVariable Long id) {
        FamilyMember familyMember = familyMemberService.getFamilyMember(id);
        FamilyMember spouse = familyMemberService.findSpouse(familyMember);

        return ResponseEntity.
                ok(familyMemberMapper.toDtoWithSpouse(familyMember, spouse));
    }

    @GetMapping("/families/{familyId}")
    public ResponseEntity<List<FamilyMemberDto>> getFamilyMembers(@PathVariable Long familyId) {
        List<FamilyMember> familyMembers = familyMemberService.findAllByFamily(familyId);

        return ResponseEntity
                .ok(familyMemberMapper.toDto(familyMembers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FamilyMemberDto> deleteFamilyMember(@PathVariable Long id) {
        FamilyMember deletedFamilyMember = familyMemberService.deleteFamilyMember(id);

        return ResponseEntity.
                ok(familyMemberMapper.toDto(deletedFamilyMember));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyMemberDto> updateFamilyMember(@PathVariable Long id, @RequestBody FamilyMemberDto familyMemberDto) {
        FamilyMember familyMember = familyMemberService.updateFamilyMember(id, familyMemberDto);
        FamilyMember spouse = familyMemberService.findSpouse(familyMember);

        return ResponseEntity
                .ok(familyMemberMapper.toDtoWithSpouse(familyMember, spouse));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FamilyMemberDto> partialUpdateFamily(@PathVariable Long id, @RequestBody FamilyMemberDto familyMemberDto) {
        FamilyMember familyMember = familyMemberService.partialUpdateFamily(id, familyMemberDto);
        FamilyMember spouse = familyMemberService.findSpouse(familyMember);

        return ResponseEntity
                .ok(familyMemberMapper.toDtoWithSpouse(familyMember, spouse));
    }
}
