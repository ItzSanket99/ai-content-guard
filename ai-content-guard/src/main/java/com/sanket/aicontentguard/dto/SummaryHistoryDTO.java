package com.sanket.aicontentguard.dto;

import com.sanket.aicontentguard.entity.RiskLevel;
import com.sanket.aicontentguard.entity.SummaryStatus;
import com.sanket.aicontentguard.entity.SummaryType;
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