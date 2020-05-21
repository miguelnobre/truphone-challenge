package com.truphone.challenge.repository;

import com.truphone.challenge.domain.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    List<Family> findAllByCountryCode(String countryCode);
}
