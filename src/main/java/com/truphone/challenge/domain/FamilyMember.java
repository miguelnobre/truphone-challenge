package com.truphone.challenge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class FamilyMember extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Family family;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FamilyMember father;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FamilyMember mother;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FamilyMember spouse;

    private String firstName;
    private String middleName;
    private String lastName;

    private LocalDate dateOfBirth;
}
