package com.sanket.aicontentguard.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ViolationStatsDTO {

    private String category;

    private Long count;
}