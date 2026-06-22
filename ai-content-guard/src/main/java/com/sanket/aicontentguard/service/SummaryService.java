package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.CreateSummaryRequestDTO;
import com.sanket.aicontentguard.dto.SummaryHistoryDTO;
import com.sanket.aicontentguard.dto.SummaryResponseDTO;
import com.sanket.aicontentguard.dto.ViolationStatsDTO;

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
