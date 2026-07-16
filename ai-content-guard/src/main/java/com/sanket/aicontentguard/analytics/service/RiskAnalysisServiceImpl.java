package com.sanket.aicontentguard.analytics.service;

import com.sanket.aicontentguard.analytics.dto.RiskAnalysisResult;
import com.sanket.aicontentguard.audit.entity.RiskLevel;
import com.sanket.aicontentguard.summary.entity.ViolationCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class RiskAnalysisServiceImpl implements RiskAnalysisService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("\\b[6-9]\\d{9}\\b");

    private static final Pattern CREDIT_CARD_PATTERN =
            Pattern.compile("\\b(?:\\d[ -]*?){13,16}\\b");

    private static final Pattern AADHAAR_PATTERN =
            Pattern.compile("\\b\\d{4}\\s?\\d{4}\\s?\\d{4}\\b");

    private static final Pattern URL_PATTERN =
            Pattern.compile("(https?://\\S+|www\\.\\S+)");

    @Override
    public RiskAnalysisResult analyze(String text) {

        int score = 0;

        List<ViolationCategory> violations =
                new ArrayList<>();

        List<String> reasons =
                new ArrayList<>();

        String lower =
                text.toLowerCase();

        if (EMAIL_PATTERN.matcher(text).find()) {

            score += 20;

            violations.add(ViolationCategory.EMAIL);

            reasons.add("Email address detected.");
        }

        if (PHONE_PATTERN.matcher(text).find()) {

            score += 20;

            violations.add(ViolationCategory.PHONE);

            reasons.add("Phone number detected.");
        }

        if (CREDIT_CARD_PATTERN.matcher(text).find()) {

            score += 40;

            violations.add(ViolationCategory.CREDIT_CARD);

            reasons.add("Credit card number detected.");
        }

        if (AADHAAR_PATTERN.matcher(text).find()) {

            score += 40;

            violations.add(ViolationCategory.AADHAAR);

            reasons.add("Aadhaar number detected.");
        }

        if (URL_PATTERN.matcher(text).find()) {

            score += 10;

            violations.add(ViolationCategory.URL);

            reasons.add("External URL detected.");
        }

        if (lower.contains("ignore previous instructions")
                || lower.contains("forget previous instructions")
                || lower.contains("you are chatgpt now")
                || lower.contains("bypass safety")) {

            score += 40;

            violations.add(ViolationCategory.PROMPT_INJECTION);

            reasons.add("Possible prompt injection attempt.");
        }

        if (lower.contains("select * from")
                || lower.contains("drop table")
                || lower.contains("' or '1'='1")
                || lower.contains("union select")) {

            score += 35;

            violations.add(ViolationCategory.SQL_INJECTION);

            reasons.add("Possible SQL Injection payload.");
        }

        if (lower.contains("apikey")
                || lower.contains("api_key")
                || lower.contains("secret")
                || lower.contains("bearer ")) {

            score += 30;

            violations.add(ViolationCategory.API_KEY);

            reasons.add("Possible secret or API key detected.");
        }

        if (lower.contains("kill")
                || lower.contains("murder")
                || lower.contains("bomb")) {

            score += 40;

            violations.add(ViolationCategory.VIOLENCE);

            reasons.add("Violence related content detected.");
        }

        if (lower.contains("hate")
                || lower.contains("racist")) {

            score += 30;

            violations.add(ViolationCategory.HATE_SPEECH);

            reasons.add("Possible hate speech detected.");
        }

        if (lower.contains("idiot")
                || lower.contains("stupid")) {

            score += 15;

            violations.add(ViolationCategory.INSULTS);

            reasons.add("Insulting language detected.");
        }

        score = Math.min(score, 100);

        RiskLevel level;

        if (score >= 80)
            level = RiskLevel.CRITICAL;
        else if (score >= 60)
            level = RiskLevel.HIGH;
        else if (score >= 30)
            level = RiskLevel.MEDIUM;
        else
            level = RiskLevel.LOW;

        return RiskAnalysisResult.builder()
                .riskScore(score)
                .riskLevel(level)
                .violations(violations)
                .reasons(reasons)
                .build();
    }
}