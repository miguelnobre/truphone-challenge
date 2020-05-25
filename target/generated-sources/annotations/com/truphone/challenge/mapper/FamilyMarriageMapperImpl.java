package com.truphone.challenge.mapper;

import com.truphone.challenge.domain.FamilyMarriage;
import com.truphone.challenge.domain.FamilyMember;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-25T22:15:11+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class FamilyMarriageMapperImpl implements FamilyMarriageMapper {

    @Override
    public FamilyMarriage toNewMarriage(FamilyMember familyMember, FamilyMember spouse) {
        if ( familyMember == null && spouse == null ) {
            return null;
        }

        FamilyMarriage familyMarriage = new FamilyMarriage();

        return familyMarriage;
    }
}
