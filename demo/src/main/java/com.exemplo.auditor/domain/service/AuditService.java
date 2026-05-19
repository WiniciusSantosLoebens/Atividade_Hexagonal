package com.exemplo.auditor.domain.service;

import com.exemplo.auditor.domain.model.AuditLog;
import com.exemplo.auditor.domain.model.DeadLetterMessage;
import com.exemplo.auditor.ports.in.ProcessDeadLetterUseCase;
import com.exemplo.auditor.ports.out.AuditRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class AuditService implements ProcessDeadLetterUseCase {

    private final AuditRepositoryPort repositoryPort;

    public AuditService(AuditRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public void execute(DeadLetterMessage message, String rawPayload) {
        AuditLog auditLog = new AuditLog(
                "T03N_WINICIUS_LOEBENS-DLQ.fifo",
                rawPayload,
                message.getTotalAmount()
        );

        repositoryPort.save(auditLog);
    }
}