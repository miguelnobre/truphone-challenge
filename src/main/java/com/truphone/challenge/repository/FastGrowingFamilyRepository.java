package com.truphone.challenge.repository;

import com.truphone.challenge.domain.view.FastGrowingFamilyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FastGrowingFamilyRepository extends JpaRepository<FastGrowingFamilyView, Long> {
}
