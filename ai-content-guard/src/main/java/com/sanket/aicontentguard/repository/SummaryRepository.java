package com.sanket.aicontentguard.repository;

import com.sanket.aicontentguard.entity.Summary;
import com.sanket.aicontentguard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    List<Summary> findByUser(User user);

    Optional<Summary> findByIdAndUser(
            Long id,
            User user
    );

    @Query("""
            SELECT AVG(s.riskScore)
            FROM Summary s
            """)
    Double getAverageRiskScore();
}
