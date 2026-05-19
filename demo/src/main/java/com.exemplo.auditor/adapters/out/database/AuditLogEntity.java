package com.exemplo.auditor.adapters.out.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
public class AuditLogEntity {
    @Id
    private UUID errorId;
    private String queueName;

    @Lob
    private String payload;
    private Instant timestamp;
    private String status;
    private String severity;

    public UUID getErrorId(){
        return this.errorId;
    }
    public void setErrorId(UUID errorId){
        this.errorId = errorId;
    }
    public String getQueueName(){
        return this.queueName;
    }
    public void setQueueName(String queueName){
        this.queueName = queueName;
    }
    public String getPayload(){
        return this.payload;
    }
    public void setPayload(String payload){
        this.payload = payload;
    }
    public Instant getTimestamp(){
        return this.timestamp;
    }
    public void setTimestamp(Instant timestamp){
        this.timestamp = timestamp;
    }
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getSeverity(){
        return this.severity;
    }
    public void setSeverity(String severity){
        this.severity = severity;
    }

}