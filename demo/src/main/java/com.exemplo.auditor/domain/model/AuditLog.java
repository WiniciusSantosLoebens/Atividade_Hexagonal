package com.exemplo.auditor.domain.model;

import java.time.Instant;
import java.util.UUID;

public class AuditLog {
    private UUID errorId;
    private String queueName;
    private String payload;
    private Instant timestamp;
    private String status;
    private String severity;

    public AuditLog(String queueName, String payload, int totalItems) {
        this.errorId = UUID.randomUUID();
        this.queueName = queueName;
        this.payload = payload;
        this.timestamp = Instant.now();
        this.status = "PENDING_ANALYSIS";
        this.severity = defineSeverity(totalItems);
    }

    private String defineSeverity(int totalItems) {
        if (totalItems > 100) return "HIGH";
        if (totalItems >= 50) return "MEDIUM";
        return "LOW";
    }

    public UUID getErrorId() { return errorId; }
    public String getQueueName() { return queueName; }
    public String getPayload() { return payload; }
    public Instant getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
    public String getSeverity() { return severity; }
}