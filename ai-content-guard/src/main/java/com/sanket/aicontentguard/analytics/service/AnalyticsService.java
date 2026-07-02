package com.sanket.aicontentguard.analytics.service;

import com.sanket.aicontentguard.analytics.dto.AdminDashboardDTO;
import com.sanket.aicontentguard.analytics.dto.RiskDistributionDTO;
import com.sanket.aicontentguard.analytics.dto.ViolationStatsDTO;

import java.util.List;

public interface AnalyticsService {

    AdminDashboardDTO getDashboardMetrics();
    List<RiskDistributionDTO> getRiskDistribution();

    List<ViolationStatsDTO> getViolationStats();
}