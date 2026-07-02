package com.sanket.aicontentguard.summary.service;

import com.sanket.aicontentguard.summary.dto.CreateSummaryRequestDTO;
import com.sanket.aicontentguard.summary.dto.SummaryHistoryDTO;
import com.sanket.aicontentguard.summary.dto.SummaryResponseDTO;

import java.util.List;


public interface SummaryService {
    SummaryResponseDTO createSummary(
            CreateSummaryRequestDTO request,
            String userEmail
    );

    List<SummaryHistoryDTO> getMySummaries(
            String email
    );

    SummaryResponseDTO getSummaryById(
            Long id,
            String email
    );


}
