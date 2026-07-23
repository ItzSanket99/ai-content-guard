package com.sanket.aicontentguard.summary.repository;

import com.sanket.aicontentguard.summary.entity.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViolationRepository extends JpaRepository<Violation, Long> {

    @Query("""
        SELECT v.category, COUNT(v)
        FROM Violation v
        GROUP BY v.category
        ORDER BY COUNT(v) DESC
        """)
    List<Object[]> findViolationStatistics();

    @Query("""
        SELECT v.category, COUNT(v)
        FROM Violation v
        GROUP BY v.category
        ORDER BY COUNT(v) DESC
        """)
    List<Object[]> getViolationStats();
}
