package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.AdminDashboardDTO;
import com.sanket.aicontentguard.dto.RiskDistributionDTO;
import com.sanket.aicontentguard.dto.ViolationStatsDTO;

import java.util.List;

public interface AnalyticsService {

    AdminDashboardDTO getDashboardMetrics();
    List<RiskDistributionDTO> getRiskDistribution();

    List<ViolationStatsDTO> getViolationStats();
}