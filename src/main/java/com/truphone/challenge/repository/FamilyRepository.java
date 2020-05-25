package com.truphone.challenge.repository;

import com.truphone.challenge.domain.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    Page<Family> findAll(Pageable pageable);

    Page<Family> findAllByCountryCode(String countryCode, Pageable pageable);
}
