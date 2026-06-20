package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.CreateSummaryRequestDTO;
import com.sanket.aicontentguard.dto.SummaryResponseDTO;



public interface SummaryService {
    SummaryResponseDTO createSummary( CreateSummaryRequestDTO request, String userEmail);
}
