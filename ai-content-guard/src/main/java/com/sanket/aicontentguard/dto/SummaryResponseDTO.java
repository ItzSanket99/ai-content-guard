package com.sanket.aicontentguard.dto;

import com.sanket.aicontentguard.entity.SummaryStatus;
import com.sanket.aicontentguard.entity.SummaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
}