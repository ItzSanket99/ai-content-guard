package com.sanket.aicontentguard.ai.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiExecutionResult {

    private String summary;

    private String provider;

    private String model;

    private long executionTimeMs;

    private String promptVersion;

    private boolean fallbackUsed;
}