package com.sanket.aicontentguard.ai.prompt;

import com.sanket.aicontentguard.summary.entity.SummaryType;
import org.springframework.stereotype.Component;

@Component
public class SummaryPromptBuilder {

    public String buildPrompt(
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
}