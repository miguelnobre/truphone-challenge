package com.truphone.challenge.mapper;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.domain.view.AgedFamilyView;
import com.truphone.challenge.dto.AgedFamilyDto;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.dto.FastGrowingFamilyDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-25T22:15:11+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class FamilyMapperImpl implements FamilyMapper {

    @Autowired
    private GenericMapper genericMapper;

    @Override
    public FamilyDto toDto(Family family) {
        if ( family == null ) {
            return null;
        }

        FamilyDto familyDto = new FamilyDto();

        familyDto.setId( family.getId() );
        familyDto.setName( family.getName() );
        familyDto.setCountryCode( family.getCountryCode() );

        return familyDto;
    }

    @Override
    public List<FamilyDto> toDto(List<Family> family) {
        if ( family == null ) {
            return null;
        }

        List<FamilyDto> list = new ArrayList<FamilyDto>( family.size() );
        for ( Family family1 : family ) {
            list.add( toDto( family1 ) );
        }

        return list;
    }

    @Override
    public Family toEntity(FamilyDto familyDto) {
        if ( familyDto == null ) {
            return null;
        }

        Family family = new Family();

        family.setName( familyDto.getName() );
        family.setCountryCode( familyDto.getCountryCode() );

        return family;
    }

    @Override
    public AgedFamilyDto toDto(Family agedFamily, AgedFamilyView agedFamilyView) {
        if ( agedFamily == null && agedFamilyView == null ) {
            return null;
        }

        AgedFamilyDto agedFamilyDto = new AgedFamilyDto();

        if ( agedFamily != null ) {
            agedFamilyDto.setId( agedFamily.getId() );
            agedFamilyDto.setName( agedFamily.getName() );
            agedFamilyDto.setCountryCode( agedFamily.getCountryCode() );
        }
        if ( agedFamilyView != null ) {
            agedFamilyDto.setTotalAge( agedFamilyView.getTotalAge() );
            agedFamilyDto.setAverageAge( genericMapper.buildBigDecimal( agedFamilyView.getAverageAge() ) );
        }

        return agedFamilyDto;
    }

    @Override
    public FastGrowingFamilyDto toDto(Family agedFamily, BigDecimal growingRate) {
        if ( agedFamily == null && growingRate == null ) {
            return null;
        }

        FastGrowingFamilyDto fastGrowingFamilyDto = new FastGrowingFamilyDto();

        if ( agedFamily != null ) {
            fastGrowingFamilyDto.setId( agedFamily.getId() );
            fastGrowingFamilyDto.setName( agedFamily.getName() );
            fastGrowingFamilyDto.setCountryCode( agedFamily.getCountryCode() );
        }
        if ( growingRate != null ) {
            fastGrowingFamilyDto.setGrowingRate( genericMapper.buildBigDecimal( growingRate ) );
        }

        return fastGrowingFamilyDto;
    }
}
