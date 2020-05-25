package com.truphone.challenge.repository;

import com.truphone.challenge.domain.view.AgedFamilyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgedFamilyRepository extends JpaRepository<AgedFamilyView, Long> {
}
