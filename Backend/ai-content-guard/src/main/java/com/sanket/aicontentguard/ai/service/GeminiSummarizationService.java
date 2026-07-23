package com.sanket.aicontentguard.ai.service;

import com.sanket.aicontentguard.ai.model.AiExecutionResult;
import com.sanket.aicontentguard.ai.pipeline.AiExecutionPipeline;
import com.sanket.aicontentguard.summary.entity.SummaryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiSummarizationService
        implements AiSummarizationService {

    private final AiExecutionPipeline pipeline;

    @Override
    public String generateSummary(
            String text,
            SummaryType summaryType
    ) {

        AiExecutionResult result =
                pipeline.execute(
                        text,
                        summaryType
                );

        return result.getSummary();
    }
}