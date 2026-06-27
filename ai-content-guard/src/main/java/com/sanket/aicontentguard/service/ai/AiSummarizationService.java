package com.sanket.aicontentguard.service.ai;

import com.sanket.aicontentguard.entity.SummaryType;

public interface AiSummarizationService {

    String generateSummary(
            String text,
            SummaryType summaryType
    );
}