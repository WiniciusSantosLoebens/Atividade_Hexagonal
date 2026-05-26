package com.winicius.deadmessages.application.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.winicius.deadmessages.core.domain.bo.DlqSeverityBO;
import com.winicius.deadmessages.core.model.ErrorAudit;
import com.winicius.deadmessages.core.model.Severity;
import com.winicius.deadmessages.core.model.Status;
import com.winicius.deadmessages.infrastructure.adapter.in.message.dlq.dto.DlqMessageDTO;
import com.winicius.deadmessages.infrastructure.adapter.out.persistence.adapter.ErrorAuditRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DlqAuditService {

    private final ErrorAuditRepositoryPort repository;

    private final ObjectMapper objectMapper;

    public void process(String payload){

        try{

            DlqMessageDTO message =
                    objectMapper.readValue(payload, DlqMessageDTO.class);

            DlqSeverityBO bo = new DlqSeverityBO();

            Severity severity =
                    bo.calculate(message.getOrderItems());

            ErrorAudit audit =
                    ErrorAudit.builder()
                            .errorId(UUID.randomUUID())
                            .queueName("WINICIUS LOEBENS")
                            .payload(payload)
                            .timestamp(Instant.now())
                            .status(Status.PENDING_ANALYSIS)
                            .severity(severity)
                            .build();

            repository.save(audit);

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}