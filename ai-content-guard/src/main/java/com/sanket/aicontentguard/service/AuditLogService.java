package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.entity.AuditAction;

public interface AuditLogService {

    void log(
            AuditAction action,
            String username,
            String details
    );
}