package com.sanket.aicontentguard.summary.dto;

import com.sanket.aicontentguard.audit.entity.RiskLevel;
import com.sanket.aicontentguard.summary.entity.ViolationCategory;
import com.sanket.aicontentguard.summary.entity.SummaryStatus;
import com.sanket.aicontentguard.summary.entity.SummaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SummaryResponseDTO {

    private Long id;

    private String originalText;

    private String summaryText;

    private SummaryStatus status;

    private Integer riskScore;

    private SummaryType summaryType;

    private RiskLevel riskLevel;

    private String aiProvider;

    private String aiModel;

    private Long executionTimeMs;

    private Boolean fallbackUsed;
    private List<String> reasons;

    private List<ViolationCategory> violations;
}