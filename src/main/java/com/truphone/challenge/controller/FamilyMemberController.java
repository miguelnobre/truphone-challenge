package com.truphone.challenge.controller;

import com.truphone.challenge.domain.FamilyMember;
import com.truphone.challenge.dto.FamilyMemberDto;
import com.truphone.challenge.mapper.FamilyMemberMapper;
import com.truphone.challenge.service.FamilyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/family-members")
public class FamilyMemberController {

    private final FamilyMemberMapper familyMemberMapper;
    private final FamilyMemberService familyMemberService;

    @GetMapping("/families/{familyId}")
    public ResponseEntity<List<FamilyMemberDto>> getFamilyMembers(@PathVariable Long familyId) {
        List<FamilyMember> familyMembers = familyMemberService.findAllByFamily(familyId);
        return ResponseEntity.ok(familyMemberMapper.toDto(familyMembers));
    }
}
