package com.truphone.challenge.controller;

import com.truphone.challenge.domain.FamilyMember;
import com.truphone.challenge.dto.FamilyMemberDto;
import com.truphone.challenge.dto.PageDto;
import com.truphone.challenge.mapper.FamilyMemberMapper;
import com.truphone.challenge.mapper.PageMapper;
import com.truphone.challenge.service.FamilyMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.net.URI;

import static com.truphone.challenge.util.Constants.DEFAULT_PAGE_SIZE;

@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("api/family-members")
public class FamilyMemberController {

    private final PageMapper pageMapper;
    private final FamilyMemberMapper familyMemberMapper;
    private final FamilyMemberService familyMemberService;

    @PostMapping
    @ApiOperation(value = "Create a Family Member")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Entity created"),
            @ApiResponse(code = 400, message = "If request data is invalid"),
            @ApiResponse(code = 404, message = "If related entities not found")
    })
    public ResponseEntity<FamilyMemberDto> createFamilyMember(@RequestBody FamilyMemberDto familyMemberDto) {
        FamilyMember familyMember = familyMemberService.createFamilyMember(familyMemberDto);
        FamilyMember spouse = familyMemberService.findSpouse(familyMember);

        return ResponseEntity
                .created(URI.create("api/family-members/" + familyMember.getId()))
                .body(familyMemberMapper.toDtoWithSpouse(familyMember, spouse));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Family Member by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entity requested"),
            @ApiResponse(code = 404, message = "Entity not found")
    })
    public ResponseEntity<FamilyMemberDto> getFamilyMember(@PathVariable Long id) {
        FamilyMember familyMember = familyMemberService.getFamilyMember(id);
        FamilyMember spouse = familyMemberService.findSpouse(familyMember);

        return ResponseEntity.ok(familyMemberMapper.toDtoWithSpouse(familyMember, spouse));
    }

    @GetMapping("/families/{familyId}")
    @ApiOperation(value = "Get Family Members that belongs to a Family")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Family Members"),
            @ApiResponse(code = 404, message = "If Family not found")

    })
    public ResponseEntity<PageDto<FamilyMemberDto>> getFamilyMembers(@PathVariable Long familyId,
                                                                     @RequestParam(defaultValue = "0") Integer offset,
                                                                     @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer limit) {

        Page<FamilyMember> familyMembers = familyMemberService.findAllByFamily(familyId, PageRequest.of(offset, limit));

        return ResponseEntity.ok(pageMapper.toDto(familyMembers, familyMemberMapper::toDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Family Member by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deleted entity"),
            @ApiResponse(code = 404, message = "If entity not found")

    })
    public ResponseEntity<FamilyMemberDto> deleteFamilyMember(@PathVariable Long id) {
        FamilyMember deletedFamilyMember = familyMemberService.deleteFamilyMember(id);

        return ResponseEntity.ok(familyMemberMapper.toDto(deletedFamilyMember));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Full update a Family Member by Id", notes = "Should be used also to delete specific field(s)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated Entity"),
            @ApiResponse(code = 400, message = "If request data is invalid"),
            @ApiResponse(code = 404, message = "Entity not found")
    })
    public ResponseEntity<FamilyMemberDto> updateFamilyMember(@PathVariable Long id, @RequestBody FamilyMemberDto familyMemberDto) {
        FamilyMember familyMember = familyMemberService.updateFamilyMember(id, familyMemberDto);
        FamilyMember spouse = familyMemberService.findSpouse(familyMember);

        return ResponseEntity.ok(familyMemberMapper.toDtoWithSpouse(familyMember, spouse));
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update partially a Family Member by Id", notes = "This method only handles adding or updating data. To delete information, PUT must be used")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Updated Entity"),
            @ApiResponse(code = 400, message = "If request data is invalid"),
            @ApiResponse(code = 404, message = "Entity not found")
    })
    public ResponseEntity<FamilyMemberDto> partialUpdateFamily(@PathVariable Long id, @RequestBody FamilyMemberDto familyMemberDto) {
        FamilyMember familyMember = familyMemberService.partialUpdateFamily(id, familyMemberDto);
        FamilyMember spouse = familyMemberService.findSpouse(familyMember);

        return ResponseEntity.ok(familyMemberMapper.toDtoWithSpouse(familyMember, spouse));
    }
}
