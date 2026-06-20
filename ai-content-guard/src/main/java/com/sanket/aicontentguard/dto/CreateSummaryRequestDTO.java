package com.sanket.aicontentguard.dto;

import com.sanket.aicontentguard.entity.SummaryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSummaryRequestDTO {

    @NotBlank
    private String text;

    @NotNull
    private SummaryType summaryType;
}