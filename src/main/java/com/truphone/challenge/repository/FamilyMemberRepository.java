package com.truphone.challenge.repository;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.domain.FamilyMarriage;
import com.truphone.challenge.domain.FamilyMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    Page<FamilyMember> findAllByFamily(Family family, Pageable pageable);

    FamilyMember findByFamilyMarriageAndIdNot(FamilyMarriage marriage, Long id);
}
