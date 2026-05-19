package com.exemplo.auditor.adapters.out.database;

import com.exemplo.auditor.domain.model.AuditLog;
import com.exemplo.auditor.ports.out.AuditRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAdapter implements AuditRepositoryPort {

    private final SpringDataAuditRepository repository;

    public DatabaseAdapter(SpringDataAuditRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(AuditLog auditLog) {
        AuditLogEntity entity = new AuditLogEntity();
        entity.setErrorId(auditLog.getErrorId());
        entity.setQueueName(auditLog.getQueueName());
        entity.setPayload(auditLog.getPayload());
        entity.setTimestamp(auditLog.getTimestamp());
        entity.setStatus(auditLog.getStatus());
        entity.setSeverity(auditLog.getSeverity());

        repository.save(entity);
    }
}