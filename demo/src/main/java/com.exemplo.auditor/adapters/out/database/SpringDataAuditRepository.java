package com.exemplo.auditor.adapters.out.database;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataAuditRepository extends JpaRepository<AuditLogEntity, UUID> {
}