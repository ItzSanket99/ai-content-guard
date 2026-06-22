package com.sanket.aicontentguard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AdminDashboardDTO {

    private Long totalUsers;

    private Long totalSummaries;

    private Long totalViolations;

    private Double averageRiskScore;

    private String mostCommonViolation;
}