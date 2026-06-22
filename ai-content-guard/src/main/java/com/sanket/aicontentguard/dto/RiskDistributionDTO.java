package com.sanket.aicontentguard.dto;

import com.sanket.aicontentguard.entity.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RiskDistributionDTO {

    private RiskLevel riskLevel;

    private Long count;
}