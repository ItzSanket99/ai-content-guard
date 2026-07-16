package com.sanket.aicontentguard.analytics.dto;

import com.sanket.aicontentguard.audit.entity.RiskLevel;
import com.sanket.aicontentguard.summary.entity.ViolationCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RiskAnalysisResult {

    private Integer riskScore;

    private RiskLevel riskLevel;

    private List<ViolationCategory> violations;
    private List<String> reasons;
}