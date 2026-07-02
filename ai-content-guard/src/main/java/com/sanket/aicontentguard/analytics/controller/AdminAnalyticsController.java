package com.sanket.aicontentguard.analytics.controller;

import com.sanket.aicontentguard.analytics.dto.AdminDashboardDTO;
import com.sanket.aicontentguard.analytics.dto.RiskDistributionDTO;
import com.sanket.aicontentguard.analytics.dto.ViolationStatsDTO;
import com.sanket.aicontentguard.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/dashboard")
    public AdminDashboardDTO dashboard() {

        return analyticsService
                .getDashboardMetrics();
    }

    @GetMapping("/risk-distribution")
    public List<RiskDistributionDTO>
    getRiskDistribution() {

        return analyticsService
                .getRiskDistribution();
    }

    @GetMapping("/violations")
    public List<ViolationStatsDTO>
    getViolationStats() {

        return analyticsService
                .getViolationStats();
    }
}