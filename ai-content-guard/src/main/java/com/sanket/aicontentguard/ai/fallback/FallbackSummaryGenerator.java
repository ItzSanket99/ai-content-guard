package com.sanket.aicontentguard.ai.fallback;

import org.springframework.stereotype.Component;

@Component
public class FallbackSummaryGenerator {

    public String generate(String text) {

        if (text == null || text.isBlank()) {
            return "";
        }

        String cleaned = text.replaceAll("\\s+", " ").trim();

        int length = Math.min(cleaned.length(), 250);

        String summary = cleaned.substring(0, length);

        if (length < cleaned.length()) {
            summary += "...";
        }

        return summary;
    }
}