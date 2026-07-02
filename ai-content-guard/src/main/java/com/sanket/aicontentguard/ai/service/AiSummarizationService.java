package com.sanket.aicontentguard.ai.service;

import com.sanket.aicontentguard.summary.entity.SummaryType;

public interface AiSummarizationService {

    String generateSummary(
            String text,
            SummaryType summaryType
    );
}