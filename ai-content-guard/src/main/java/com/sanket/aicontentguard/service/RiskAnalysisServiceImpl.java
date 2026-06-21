package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.RiskAnalysisResult;
import com.sanket.aicontentguard.entity.RiskLevel;
import com.sanket.aicontentguard.entity.ViolationCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RiskAnalysisServiceImpl
        implements RiskAnalysisService {

    @Override
    public RiskAnalysisResult analyze(String text) {

        int score = 0;

        List<ViolationCategory> violations =
                new ArrayList<>();

        String lower = text.toLowerCase();

        if(lower.contains("@")) {
            score += 30;
            violations.add(
                    ViolationCategory.PII
            );
        }

        if(lower.contains("ignore previous instructions")) {
            score += 40;
            violations.add(
                    ViolationCategory.PROMPT_INJECTION
            );
        }

        if(lower.contains("kill")) {
            score += 50;
            violations.add(
                    ViolationCategory.VIOLENCE
            );
        }

        RiskLevel level;

        if(score >= 80)
            level = RiskLevel.CRITICAL;
        else if(score >= 60)
            level = RiskLevel.HIGH;
        else if(score >= 30)
            level = RiskLevel.MEDIUM;
        else
            level = RiskLevel.LOW;

        return RiskAnalysisResult.builder()
                .riskScore(score)
                .riskLevel(level)
                .violations(violations)
                .build();
    }
}