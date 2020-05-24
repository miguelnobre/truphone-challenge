package com.truphone.challenge.repository;

import com.truphone.challenge.domain.FamilyMarriage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyMarriageRepository extends JpaRepository<FamilyMarriage, Long> {
}
