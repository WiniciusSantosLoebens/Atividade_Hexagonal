package com.exemplo.auditor.ports.out;

import com.exemplo.auditor.domain.model.AuditLog;

public interface AuditRepositoryPort {
    void save(AuditLog auditLog);
}