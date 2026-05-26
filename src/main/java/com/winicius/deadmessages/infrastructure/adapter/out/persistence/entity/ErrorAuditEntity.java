package com.winicius.deadmessages.infrastructure.adapter.out.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import com.winicius.deadmessages.core.model.Severity;
import com.winicius.deadmessages.core.model.Status;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_error_audit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorAuditEntity {

    @Id
    private UUID errorId;

    private String queueName;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private Instant timestamp;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Severity severity;

}
