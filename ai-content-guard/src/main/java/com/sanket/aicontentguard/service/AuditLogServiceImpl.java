package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.entity.AuditAction;
import com.sanket.aicontentguard.entity.AuditLog;
import com.sanket.aicontentguard.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl
        implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void log(
            AuditAction action,
            String username,
            String details
    ) {

        AuditLog auditLog =
                AuditLog.builder()
                        .action(action)
                        .username(username)
                        .details(details)
                        .build();

        auditLogRepository.save(auditLog);
    }
}