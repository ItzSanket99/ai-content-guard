package com.sanket.aicontentguard.ai.pipeline;

import com.sanket.aicontentguard.ai.model.AiExecutionResult;
import com.sanket.aicontentguard.ai.provider.AiProvider;
import com.sanket.aicontentguard.ai.prompt.SummaryPromptBuilder;
import com.sanket.aicontentguard.summary.entity.SummaryType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiExecutionPipeline {

    private final SummaryPromptBuilder promptBuilder;

    private final AiProvider aiProvider;

    @Value("${gemini.model}")
    private String model;

    public AiExecutionResult execute(
            String text,
            SummaryType summaryType
    ) {

        long start = System.currentTimeMillis();

        String prompt =
                promptBuilder.buildPrompt(
                        text,
                        summaryType
                );

        String summary =
                aiProvider.generateContent(
                        prompt
                );

        long end = System.currentTimeMillis();

        return AiExecutionResult.builder()
                .summary(summary)
                .provider("Gemini")
                .model(model)
                .executionTimeMs(end - start)
                .promptVersion("v1")
                .fallbackUsed(false)
                .build();
    }

}