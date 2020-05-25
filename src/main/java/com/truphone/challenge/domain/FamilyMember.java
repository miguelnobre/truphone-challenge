package com.truphone.challenge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class FamilyMember extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Family family;

    @ManyToOne(fetch = FetchType.LAZY)
    private FamilyMember father;

    @ManyToOne(fetch = FetchType.LAZY)
    private FamilyMember mother;

    @OneToOne(fetch = FetchType.LAZY)
    private FamilyMarriage familyMarriage;

    private String firstName;
    private String middleName;
    private String lastName;

    private LocalDate dateOfBirth;
}
