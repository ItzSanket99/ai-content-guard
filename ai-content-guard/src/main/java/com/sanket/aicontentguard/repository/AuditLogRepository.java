package com.sanket.aicontentguard.repository;

import com.sanket.aicontentguard.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
