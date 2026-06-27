package com.sanket.aicontentguard.service.ai;

import com.google.genai.Client;
import com.sanket.aicontentguard.entity.SummaryType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiSummarizationService
        implements AiSummarizationService {

    private String buildPrompt(
            String text,
            SummaryType summaryType
    ) {

        return switch (summaryType) {

            case SHORT -> """
                Summarize the following text in exactly 2-3 concise sentences.
                
                Text:
                %s
                """.formatted(text);

            case DETAILED -> """
                Generate a detailed summary while preserving important technical information.
                
                Text:
                %s
                """.formatted(text);

            case BULLET -> """
                Summarize the following text into bullet points.
                Maximum 6 bullets.
                
                Text:
                %s
                """.formatted(text);

            case EXECUTIVE -> """
                Act as a senior business analyst.
                
                Generate an executive summary including:
                - Key Decisions
                - Risks
                - Action Items
                
                Text:
                %s
                """.formatted(text);
        };
    }

    private final Client geminiClient;

    @Value("${gemini.model}")
    private String model;

    @Override
    public String generateSummary(String text, SummaryType summaryType) {

        String prompt = buildPrompt(text, summaryType);

        try {

            var response = geminiClient.models.generateContent(
                    model,
                    prompt,
                    null
            );

            return response.text();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to generate summary using Gemini",
                    ex
            );
        }
    }

}