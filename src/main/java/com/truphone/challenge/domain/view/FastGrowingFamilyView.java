package com.truphone.challenge.domain.view;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Immutable
@Entity(name = "v_fast_growing_family")
public class FastGrowingFamilyView {

    @Id
    private Long familyId;

    private BigDecimal growingRate;

    private Long totalMembers;
}
