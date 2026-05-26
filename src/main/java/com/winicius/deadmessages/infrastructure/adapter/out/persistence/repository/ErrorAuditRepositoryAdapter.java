package com.winicius.deadmessages.infrastructure.adapter.out.persistence.repository;

import org.springframework.stereotype.Component;

import com.winicius.deadmessages.core.model.ErrorAudit;
import com.winicius.deadmessages.infrastructure.adapter.out.persistence.adapter.ErrorAuditRepositoryPort;
import com.winicius.deadmessages.infrastructure.adapter.out.persistence.entity.ErrorAuditEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ErrorAuditRepositoryAdapter
        implements ErrorAuditRepositoryPort {

    private final ErrorAuditJpaRepository repository;

    @Override
    public void save(ErrorAudit audit) {

        ErrorAuditEntity entity =
                ErrorAuditEntity.builder()
                        .errorId(audit.getErrorId())
                        .queueName(audit.getQueueName())
                        .payload(audit.getPayload())
                        .timestamp(audit.getTimestamp())
                        .status(audit.getStatus())
                        .severity(audit.getSeverity())
                        .build();

        repository.save(entity);
    }
}
