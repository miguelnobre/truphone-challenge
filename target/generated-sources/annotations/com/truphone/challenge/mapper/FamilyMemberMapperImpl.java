package com.truphone.challenge.mapper;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.domain.FamilyMember;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.dto.FamilyMemberDto;
import com.truphone.challenge.dto.FamilyPersonDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-25T22:15:11+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class FamilyMemberMapperImpl implements FamilyMemberMapper {

    @Override
    public FamilyPersonDto toFamilyPersonDto(FamilyMember familyMember) {
        if ( familyMember == null ) {
            return null;
        }

        FamilyPersonDto familyPersonDto = new FamilyPersonDto();

        familyPersonDto.setId( familyMember.getId() );
        familyPersonDto.setFirstName( familyMember.getFirstName() );
        familyPersonDto.setMiddleName( familyMember.getMiddleName() );
        familyPersonDto.setLastName( familyMember.getLastName() );
        familyPersonDto.setDateOfBirth( familyMember.getDateOfBirth() );

        return familyPersonDto;
    }

    @Override
    public FamilyMemberDto toDto(FamilyMember familyMember) {
        if ( familyMember == null ) {
            return null;
        }

        FamilyMemberDto familyMemberDto = new FamilyMemberDto();

        familyMemberDto.setMe( familyMemberToFamilyPersonDto( familyMember ) );
        familyMemberDto.setId( familyMember.getId() );
        familyMemberDto.setFamily( familyToFamilyDto( familyMember.getFamily() ) );
        familyMemberDto.setFather( toFamilyPersonDto( familyMember.getFather() ) );
        familyMemberDto.setMother( toFamilyPersonDto( familyMember.getMother() ) );

        return familyMemberDto;
    }

    @Override
    public FamilyMemberDto toDtoWithSpouse(FamilyMember familyMember, FamilyMember spouse) {
        if ( familyMember == null && spouse == null ) {
            return null;
        }

        FamilyMemberDto familyMemberDto = new FamilyMemberDto();

        if ( familyMember != null ) {
            familyMemberDto.setMe( familyMemberToFamilyPersonDto1( familyMember ) );
            familyMemberDto.setMother( toFamilyPersonDto( familyMember.getMother() ) );
            familyMemberDto.setFather( toFamilyPersonDto( familyMember.getFather() ) );
            familyMemberDto.setId( familyMember.getId() );
            familyMemberDto.setFamily( familyToFamilyDto( familyMember.getFamily() ) );
        }
        if ( spouse != null ) {
            familyMemberDto.setSpouse( toFamilyPersonDto( spouse ) );
        }

        return familyMemberDto;
    }

    @Override
    public List<FamilyMemberDto> toDto(List<FamilyMember> familyMember) {
        if ( familyMember == null ) {
            return null;
        }

        List<FamilyMemberDto> list = new ArrayList<FamilyMemberDto>( familyMember.size() );
        for ( FamilyMember familyMember1 : familyMember ) {
            list.add( toDto( familyMember1 ) );
        }

        return list;
    }

    @Override
    public FamilyMember toEntity(FamilyMemberDto familyMemberDto) {
        if ( familyMemberDto == null ) {
            return null;
        }

        FamilyMember familyMember = new FamilyMember();

        familyMember.setFirstName( familyMemberDtoMeFirstName( familyMemberDto ) );
        familyMember.setLastName( familyMemberDtoMeLastName( familyMemberDto ) );
        familyMember.setMiddleName( familyMemberDtoMeMiddleName( familyMemberDto ) );
        familyMember.setDateOfBirth( familyMemberDtoMeDateOfBirth( familyMemberDto ) );
        familyMember.setId( familyMemberDto.getId() );
        familyMember.setFamily( familyDtoToFamily( familyMemberDto.getFamily() ) );
        familyMember.setFather( familyPersonDtoToFamilyMember( familyMemberDto.getFather() ) );
        familyMember.setMother( familyPersonDtoToFamilyMember( familyMemberDto.getMother() ) );

        return familyMember;
    }

    protected FamilyPersonDto familyMemberToFamilyPersonDto(FamilyMember familyMember) {
        if ( familyMember == null ) {
            return null;
        }

        FamilyPersonDto familyPersonDto = new FamilyPersonDto();

        familyPersonDto.setDateOfBirth( familyMember.getDateOfBirth() );
        familyPersonDto.setLastName( familyMember.getLastName() );
        familyPersonDto.setMiddleName( familyMember.getMiddleName() );
        familyPersonDto.setFirstName( familyMember.getFirstName() );

        return familyPersonDto;
    }

    protected FamilyDto familyToFamilyDto(Family family) {
        if ( family == null ) {
            return null;
        }

        FamilyDto familyDto = new FamilyDto();

        familyDto.setId( family.getId() );
        familyDto.setName( family.getName() );
        familyDto.setCountryCode( family.getCountryCode() );

        return familyDto;
    }

    protected FamilyPersonDto familyMemberToFamilyPersonDto1(FamilyMember familyMember) {
        if ( familyMember == null ) {
            return null;
        }

        FamilyPersonDto familyPersonDto = new FamilyPersonDto();

        familyPersonDto.setLastName( familyMember.getLastName() );
        familyPersonDto.setMiddleName( familyMember.getMiddleName() );
        familyPersonDto.setDateOfBirth( familyMember.getDateOfBirth() );
        familyPersonDto.setFirstName( familyMember.getFirstName() );

        return familyPersonDto;
    }

    private String familyMemberDtoMeFirstName(FamilyMemberDto familyMemberDto) {
        if ( familyMemberDto == null ) {
            return null;
        }
        FamilyPersonDto me = familyMemberDto.getMe();
        if ( me == null ) {
            return null;
        }
        String firstName = me.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String familyMemberDtoMeLastName(FamilyMemberDto familyMemberDto) {
        if ( familyMemberDto == null ) {
            return null;
        }
        FamilyPersonDto me = familyMemberDto.getMe();
        if ( me == null ) {
            return null;
        }
        String lastName = me.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String familyMemberDtoMeMiddleName(FamilyMemberDto familyMemberDto) {
        if ( familyMemberDto == null ) {
            return null;
        }
        FamilyPersonDto me = familyMemberDto.getMe();
        if ( me == null ) {
            return null;
        }
        String middleName = me.getMiddleName();
        if ( middleName == null ) {
            return null;
        }
        return middleName;
    }

    private LocalDate familyMemberDtoMeDateOfBirth(FamilyMemberDto familyMemberDto) {
        if ( familyMemberDto == null ) {
            return null;
        }
        FamilyPersonDto me = familyMemberDto.getMe();
        if ( me == null ) {
            return null;
        }
        LocalDate dateOfBirth = me.getDateOfBirth();
        if ( dateOfBirth == null ) {
            return null;
        }
        return dateOfBirth;
    }

    protected Family familyDtoToFamily(FamilyDto familyDto) {
        if ( familyDto == null ) {
            return null;
        }

        Family family = new Family();

        family.setId( familyDto.getId() );
        family.setName( familyDto.getName() );
        family.setCountryCode( familyDto.getCountryCode() );

        return family;
    }

    protected FamilyMember familyPersonDtoToFamilyMember(FamilyPersonDto familyPersonDto) {
        if ( familyPersonDto == null ) {
            return null;
        }

        FamilyMember familyMember = new FamilyMember();

        familyMember.setId( familyPersonDto.getId() );
        familyMember.setFirstName( familyPersonDto.getFirstName() );
        familyMember.setMiddleName( familyPersonDto.getMiddleName() );
        familyMember.setLastName( familyPersonDto.getLastName() );
        familyMember.setDateOfBirth( familyPersonDto.getDateOfBirth() );

        return familyMember;
    }
}
