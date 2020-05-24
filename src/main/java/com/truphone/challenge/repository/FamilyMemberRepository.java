package com.truphone.challenge.repository;

import com.truphone.challenge.domain.Family;
import com.truphone.challenge.domain.FamilyMarriage;
import com.truphone.challenge.domain.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    List<FamilyMember> findAllByFamily(Family family);

    FamilyMember findByFamilyMarriageAndIdNot(FamilyMarriage marriage, Long id);
}
