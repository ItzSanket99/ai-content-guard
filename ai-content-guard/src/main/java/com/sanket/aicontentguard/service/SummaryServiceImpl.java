package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.CreateSummaryRequestDTO;
import com.sanket.aicontentguard.dto.RiskAnalysisResult;
import com.sanket.aicontentguard.dto.SummaryResponseDTO;
import com.sanket.aicontentguard.entity.Summary;
import com.sanket.aicontentguard.entity.SummaryStatus;
import com.sanket.aicontentguard.entity.User;
import com.sanket.aicontentguard.entity.Violation;
import com.sanket.aicontentguard.repository.SummaryRepository;
import com.sanket.aicontentguard.repository.UserRepository;
import com.sanket.aicontentguard.repository.ViolationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                request.getText().length() > 100
                        ? request.getText().substring(0, 100) + "..."
                        : request.getText();

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

        for(var category : riskResult.getViolations()) {

            Violation violation =
                    Violation.builder()
                            .category(category)
                            .score(riskResult.getRiskScore())
                            .description(category.name())
                            .summary(saved)
                            .build();

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
}
