package com.sanket.aicontentguard.summary.controller;

import com.sanket.aicontentguard.common.response.ApiResponse;
import com.sanket.aicontentguard.common.response.ApiResponseBuilder;
import com.sanket.aicontentguard.summary.service.SummaryService;
import com.sanket.aicontentguard.summary.dto.CreateSummaryRequestDTO;
import com.sanket.aicontentguard.summary.dto.SummaryHistoryDTO;
import com.sanket.aicontentguard.summary.dto.SummaryResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/summaries")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @PostMapping
    public ResponseEntity<ApiResponse<SummaryResponseDTO>> createSummary(
            @Valid @RequestBody CreateSummaryRequestDTO request
    ) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        SummaryResponseDTO response =
                summaryService.createSummary(request, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(
                ApiResponseBuilder.success(
                        "Summary generated successfully",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SummaryHistoryDTO>>> getMySummaries() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "summaries fetch successfully",
                        summaryService.getMySummaries(
                                email
                        )
                )
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SummaryResponseDTO>> getSummaryById(
            @PathVariable Long id
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "summary fetch successfully",
                        summaryService.getSummaryById(
                                id,
                                email
                        )
                )
        );
    }
}