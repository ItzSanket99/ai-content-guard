package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.RiskAnalysisResult;

public interface RiskAnalysisService {

    RiskAnalysisResult analyze(String text);
}