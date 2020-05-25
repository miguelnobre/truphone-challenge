package com.truphone.challenge.domain.view;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Immutable
@Entity(name = "v_aged_family")
public class AgedFamilyView {

    @Id
    private Long familyId;
    private int totalAge;
    private BigDecimal averageAge;
}
