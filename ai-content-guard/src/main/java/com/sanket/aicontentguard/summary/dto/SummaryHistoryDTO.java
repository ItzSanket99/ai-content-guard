package com.sanket.aicontentguard.summary.dto;

import com.sanket.aicontentguard.audit.entity.RiskLevel;
import com.sanket.aicontentguard.summary.entity.SummaryStatus;
import com.sanket.aicontentguard.summary.entity.SummaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class SummaryHistoryDTO {

    private Long id;

    private SummaryType summaryType;

    private SummaryStatus status;

    private Integer riskScore;

    private RiskLevel riskLevel;

    private LocalDateTime createdAt;
}