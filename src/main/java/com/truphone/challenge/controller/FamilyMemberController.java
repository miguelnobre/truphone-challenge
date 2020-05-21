package com.truphone.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/family-members")
public class FamilyMemberController {

    @GetMapping
    public ResponseEntity<String> dummy() {
        return ResponseEntity.ok("FamilyMemberController");
    }
}
