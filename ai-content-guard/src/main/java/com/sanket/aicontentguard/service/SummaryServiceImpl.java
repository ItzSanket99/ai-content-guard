package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.CreateSummaryRequestDTO;
import com.sanket.aicontentguard.dto.RiskAnalysisResult;
import com.sanket.aicontentguard.dto.SummaryHistoryDTO;
import com.sanket.aicontentguard.dto.SummaryResponseDTO;
import com.sanket.aicontentguard.entity.*;
import com.sanket.aicontentguard.repository.SummaryRepository;
import com.sanket.aicontentguard.repository.UserRepository;
import com.sanket.aicontentguard.repository.ViolationRepository;
import com.sanket.aicontentguard.service.ai.AiSummarizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SummaryRepository summaryRepository;

    @Autowired
    private RiskAnalysisService riskAnalysisService;

    @Autowired
    private ViolationRepository violationRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private AiSummarizationService aiSummarizationService;

    @Override
    public SummaryResponseDTO createSummary(CreateSummaryRequestDTO request, String userEmail) {

        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        RiskAnalysisResult riskResult =
                riskAnalysisService.analyze(
                        request.getText()
                );

        String generatedSummary =
                aiSummarizationService.generateSummary(
                        request.getText(),
                        request.getSummaryType()
                );

        Summary summary = Summary.builder()
                .originalText(request.getText())
                .summaryText(generatedSummary)
                .summaryType(request.getSummaryType())
                .status(SummaryStatus.COMPLETED)
                .riskScore(riskResult.getRiskScore())
                .riskLevel(riskResult.getRiskLevel())
                .user(user)
                .build();

        Summary saved =
                summaryRepository.save(summary);

        auditLogService.log(
                AuditAction.SUMMARY_CREATED,
                user.getEmail(),
                "Summary ID: " + saved.getId()
        );

        for(var category : riskResult.getViolations()) {

            Violation violation =
                    Violation.builder()
                            .category(category)
                            .score(riskResult.getRiskScore())
                            .description(category.name())
                            .summary(saved)
                            .build();

            auditLogService.log(
                    AuditAction.VIOLATION_DETECTED,
                    user.getEmail(),
                    category.name()
            );

            violationRepository.save(violation);
        }
        return SummaryResponseDTO.builder()
                .id(saved.getId())
                .originalText(saved.getOriginalText())
                .summaryText(saved.getSummaryText())
                .status(saved.getStatus())
                .riskScore(saved.getRiskScore())
                .riskLevel(saved.getRiskLevel())
                .violations(
                        riskResult.getViolations()
                )
                .summaryType(saved.getSummaryType())
                .build();
    }

    @Override
    public List<SummaryHistoryDTO> getMySummaries(
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        return summaryRepository
                .findByUser(user)
                .stream()
                .map(summary ->
                        SummaryHistoryDTO.builder()
                                .id(summary.getId())
                                .summaryType(summary.getSummaryType())
                                .status(summary.getStatus())
                                .riskScore(summary.getRiskScore())
                                .riskLevel(summary.getRiskLevel())
                                .createdAt(summary.getCreatedAt())
                                .build()
                )
                .toList();
    }

    @Override
    public SummaryResponseDTO getSummaryById(
            Long id,
            String email
    ) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );

        Summary summary = summaryRepository
                .findByIdAndUser(id, user)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Summary not found"
                        )
                );

        return SummaryResponseDTO.builder()
                .id(summary.getId())
                .originalText(summary.getOriginalText())
                .summaryText(summary.getSummaryText())
                .status(summary.getStatus())
                .riskScore(summary.getRiskScore())
                .riskLevel(summary.getRiskLevel())
                .summaryType(summary.getSummaryType())
                .build();
    }
}
