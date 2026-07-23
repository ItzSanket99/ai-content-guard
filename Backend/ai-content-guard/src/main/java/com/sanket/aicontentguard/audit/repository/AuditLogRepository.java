package com.sanket.aicontentguard.audit.repository;

import com.sanket.aicontentguard.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
