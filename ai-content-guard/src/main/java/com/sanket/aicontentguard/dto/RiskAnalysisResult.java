package com.sanket.aicontentguard.dto;

import com.sanket.aicontentguard.entity.RiskLevel;
import com.sanket.aicontentguard.entity.ViolationCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RiskAnalysisResult {

    private Integer riskScore;

    private RiskLevel riskLevel;

    private List<ViolationCategory> violations;
}