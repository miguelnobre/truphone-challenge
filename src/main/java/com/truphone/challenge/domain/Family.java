package com.truphone.challenge.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@Entity
public class Family extends AbstractEntity {
    private String name;
    private String countryCode;

    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
    private List<FamilyMember> familyMemberList;
}
