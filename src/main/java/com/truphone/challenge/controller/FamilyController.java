package com.truphone.challenge.controller;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.dto.AgedFamilyDto;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.dto.FastGrowingFamilyDto;
import com.truphone.challenge.dto.PageDto;
import com.truphone.challenge.exception.FamilyNotFoundException;
import com.truphone.challenge.mapper.FamilyMapper;
import com.truphone.challenge.mapper.PageMapper;
import com.truphone.challenge.service.FamilyService;
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

//The transaction needs to start at the Controller level due to the lazy load that occurs during the mapping
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("api/families")
public class FamilyController {

    private final PageMapper pageMapper;
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

        return ResponseEntity.ok(familyMapper.toDto(family));
    }

    @GetMapping
    public ResponseEntity<PageDto<FamilyDto>> getAllFamilies(@RequestParam(defaultValue = "0") Integer offset,
                                                             @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer limit) {

        Page<Family> families = familyService.getAllFamilies(PageRequest.of(offset, limit));

        return ResponseEntity.ok(pageMapper.toDto(families, familyMapper::toDto));
    }

    @GetMapping("/country/{isoCountryCode}")
    public ResponseEntity<PageDto<FamilyDto>> getFamiliesByCountryCode(@PathVariable String isoCountryCode,
                                                                       @RequestParam(defaultValue = "0") Integer offset,
                                                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer limit) {

        Page<Family> familiesByCountryCode = familyService.getFamiliesByCountryCode(isoCountryCode, PageRequest.of(offset, limit));

        return ResponseEntity.ok(pageMapper.toDto(familiesByCountryCode, familyMapper::toDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FamilyDto> deleteFamily(@PathVariable Long id) {
        Family deletedFmily = familyService.deleteFamily(id);

        return ResponseEntity.ok(familyMapper.toDto(deletedFmily));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyDto> updateFamily(@PathVariable Long id, @RequestBody FamilyDto familyDto) {
        Family family = familyService.updateFamily(id, familyDto);

        return ResponseEntity.ok(familyMapper.toDto(family));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FamilyDto> partialUpdateFamily(@PathVariable Long id, @RequestBody FamilyDto familyDto) {
        Family family = familyService.partialUpdateFamily(id, familyDto);

        return ResponseEntity.ok(familyMapper.toDto(family));
    }

    @GetMapping("/aged")
    @ApiOperation(
            value = "Returns the Family with the most accumulated age",
            notes = "If there are 2 families with the same accumulated age, the one with the highest average age will be chosen")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 204, message = "If the are no Families stored in DB"),
    })
    public ResponseEntity<AgedFamilyDto> findAgedFamily() {
        AgedFamilyDto agedFamilyDto = familyService.findAgedFamily();

        if (agedFamilyDto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(agedFamilyDto);
    }

    @GetMapping("/fast-growing")
    @ApiOperation(
            value = "Returns the Family with Fast-Growing Ratio",
            notes = "If there are 2 families with the same ratio, the one with the most members will be chosen")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fast-Growing Family"),
            @ApiResponse(code = 204, message = "If the are no Families stored in DB"),
    })
    public ResponseEntity<FastGrowingFamilyDto> findFastGrowingFamily() {
        FastGrowingFamilyDto fastGrowingFamilyDto = familyService.findFastGrowingFamily();

        if (fastGrowingFamilyDto == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(fastGrowingFamilyDto);
    }
}
