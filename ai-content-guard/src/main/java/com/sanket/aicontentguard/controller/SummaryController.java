package com.sanket.aicontentguard.controller;

import com.sanket.aicontentguard.dto.CreateSummaryRequestDTO;
import com.sanket.aicontentguard.dto.SummaryHistoryDTO;
import com.sanket.aicontentguard.dto.SummaryResponseDTO;
import com.sanket.aicontentguard.service.SummaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/summaries")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @PostMapping
    public SummaryResponseDTO createSummary(
            @Valid
            @RequestBody
            CreateSummaryRequestDTO request) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return summaryService
                .createSummary(request, email);
    }

    @GetMapping
    public List<SummaryHistoryDTO> getMySummaries() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return summaryService.getMySummaries(
                email
        );

    }

    @GetMapping("/{id}")
    public SummaryResponseDTO getSummaryById(
            @PathVariable Long id
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return summaryService.getSummaryById(
                id,
                email
        );
    }
}