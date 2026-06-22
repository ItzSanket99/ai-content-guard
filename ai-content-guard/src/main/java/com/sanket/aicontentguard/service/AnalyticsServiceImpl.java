package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.AdminDashboardDTO;
import com.sanket.aicontentguard.dto.RiskDistributionDTO;
import com.sanket.aicontentguard.dto.ViolationStatsDTO;
import com.sanket.aicontentguard.entity.RiskLevel;
import com.sanket.aicontentguard.repository.SummaryRepository;
import com.sanket.aicontentguard.repository.UserRepository;
import com.sanket.aicontentguard.repository.ViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl
        implements AnalyticsService {

    private final UserRepository userRepository;

    private final SummaryRepository summaryRepository;

    private final ViolationRepository violationRepository;

    @Override
    public AdminDashboardDTO getDashboardMetrics() {

        Long totalUsers =
                userRepository.count();

        Long totalSummaries =
                summaryRepository.count();

        Long totalViolations =
                violationRepository.count();

        Double averageRiskScore =
                summaryRepository
                        .getAverageRiskScore();

        String mostCommonViolation =
                "NONE";

        List<Object[]> stats =
                violationRepository
                        .findViolationStatistics();

        if(!stats.isEmpty()) {

            mostCommonViolation =
                    stats.get(0)[0]
                            .toString();
        }

        return AdminDashboardDTO.builder()
                .totalUsers(totalUsers)
                .totalSummaries(totalSummaries)
                .totalViolations(totalViolations)
                .averageRiskScore(
                        averageRiskScore == null
                                ? 0
                                : averageRiskScore
                )
                .mostCommonViolation(
                        mostCommonViolation
                )
                .build();
    }

    @Override
    public List<RiskDistributionDTO> getRiskDistribution() {

        List<Object[]> result =
                summaryRepository
                        .getRiskDistribution();

        return result.stream()
                .map(row ->
                        RiskDistributionDTO
                                .builder()
                                .riskLevel(
                                        (RiskLevel) row[0]
                                )
                                .count(
                                        (Long) row[1]
                                )
                                .build()
                )
                .toList();
    }


    @Override
    public List<ViolationStatsDTO>
    getViolationStats() {

        return violationRepository
                .getViolationStats()
                .stream()
                .map(row ->
                        ViolationStatsDTO
                                .builder()
                                .category(
                                        row[0].toString()
                                )
                                .count(
                                        (Long) row[1]
                                )
                                .build()
                )
                .toList();
    }
}