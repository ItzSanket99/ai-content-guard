package com.sanket.aicontentguard.analytics.service;

import com.sanket.aicontentguard.analytics.dto.RiskAnalysisResult;

public interface RiskAnalysisService {

    RiskAnalysisResult analyze(String text);
}