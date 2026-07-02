package com.sanket.aicontentguard.analytics.controller;

import com.sanket.aicontentguard.analytics.dto.AdminDashboardDTO;
import com.sanket.aicontentguard.analytics.dto.RiskDistributionDTO;
import com.sanket.aicontentguard.analytics.dto.ViolationStatsDTO;
import com.sanket.aicontentguard.analytics.service.AnalyticsService;
import com.sanket.aicontentguard.common.ApiResponse;
import com.sanket.aicontentguard.common.ApiResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<AdminDashboardDTO>> dashboard() {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "DashBoard Data fetch successfully",
                        analyticsService
                                .getDashboardMetrics()
                )
        );

    }

    @GetMapping("/risk-distribution")
    public ResponseEntity<ApiResponse<List<RiskDistributionDTO>>>
    getRiskDistribution() {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "DashBoard risk Data fetch successfully",
                        analyticsService
                                .getRiskDistribution()
                )
        );
    }

    @GetMapping("/violations")
    public ResponseEntity<ApiResponse<List<ViolationStatsDTO>>>
    getViolationStats() {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "DashBoard violation Data fetch successfully",
                        analyticsService
                                .getViolationStats()
                )
        );
    }
}