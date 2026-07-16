package com.sanket.aicontentguard.summary.service;

import com.sanket.aicontentguard.ai.model.AiExecutionResult;
import com.sanket.aicontentguard.ai.pipeline.AiExecutionPipeline;
import com.sanket.aicontentguard.audit.entity.AuditAction;
import com.sanket.aicontentguard.auth.entity.User;
import com.sanket.aicontentguard.analytics.dto.RiskAnalysisResult;
import com.sanket.aicontentguard.summary.repository.SummaryRepository;
import com.sanket.aicontentguard.auth.repository.UserRepository;
import com.sanket.aicontentguard.summary.repository.ViolationRepository;
import com.sanket.aicontentguard.audit.service.AuditLogService;
import com.sanket.aicontentguard.analytics.service.RiskAnalysisService;
import com.sanket.aicontentguard.ai.service.AiSummarizationService;
import com.sanket.aicontentguard.summary.dto.CreateSummaryRequestDTO;
import com.sanket.aicontentguard.summary.dto.SummaryHistoryDTO;
import com.sanket.aicontentguard.summary.dto.SummaryResponseDTO;
import com.sanket.aicontentguard.summary.entity.Summary;
import com.sanket.aicontentguard.summary.entity.SummaryStatus;
import com.sanket.aicontentguard.summary.entity.Violation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SummaryServiceImpl implements SummaryService{

    private final UserRepository userRepository;

    private final SummaryRepository summaryRepository;

    private final RiskAnalysisService riskAnalysisService;

    private final ViolationRepository violationRepository;

    private final AuditLogService   auditLogService;

    private final AiExecutionPipeline aiExecutionPipeline;

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

        AiExecutionResult aiResult =
                aiExecutionPipeline.execute(
                        request.getText(),
                        request.getSummaryType()
                );

        Summary summary = Summary.builder()
                .originalText(request.getText())
                .summaryText(aiResult.getSummary())
                .summaryType(request.getSummaryType())
                .status(SummaryStatus.COMPLETED)
                .riskScore(riskResult.getRiskScore())
                .riskLevel(riskResult.getRiskLevel())
                .aiProvider(aiResult.getProvider())
                .aiModel(aiResult.getModel())
                .promptVersion(aiResult.getPromptVersion())
                .executionTimeMs(aiResult.getExecutionTimeMs())
                .fallbackUsed(aiResult.isFallbackUsed())
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
                .aiProvider(saved.getAiProvider())
                .aiModel(saved.getAiModel())
                .executionTimeMs(saved.getExecutionTimeMs())
                .fallbackUsed(saved.getFallbackUsed())
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
                .aiProvider(summary.getAiProvider())
                .aiModel(summary.getAiModel())
                .executionTimeMs(summary.getExecutionTimeMs())
                .fallbackUsed(summary.getFallbackUsed())
                .build();
    }
}
