package com.sanket.aicontentguard.audit.service;

import com.sanket.aicontentguard.audit.entity.AuditAction;

public interface AuditLogService {

    void log(
            AuditAction action,
            String username,
            String details
    );
}