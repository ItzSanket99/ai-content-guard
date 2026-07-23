package com.sanket.aicontentguard.ai.provider;

import com.google.genai.Client;
import com.sanket.aicontentguard.ai.exception.AiServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeminiProvider implements AiProvider {

    private final Client geminiClient;

    @Value("${gemini.model}")
    private String model;

    @Override
    public String generateContent(String prompt) {

        try {

            var response = geminiClient.models.generateContent(
                    model,
                    prompt,
                    null
            );

            return response.text();

        } catch (Exception ex) {

            throw new AiServiceException(
                    "Failed to communicate with Gemini",
                    ex
            );
        }
    }
}