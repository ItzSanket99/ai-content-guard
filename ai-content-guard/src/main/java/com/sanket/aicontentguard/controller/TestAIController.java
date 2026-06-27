package com.sanket.aicontentguard.controller;

import com.sanket.aicontentguard.entity.SummaryType;
import com.sanket.aicontentguard.service.ai.AiSummarizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestAIController {

    private final AiSummarizationService aiSummarizationService;

    @PostMapping("/gemini")
    public String testGemini(
            @RequestBody String text
    ) {

        return aiSummarizationService.generateSummary(
                text,
                SummaryType.BULLET
        );
    }
}