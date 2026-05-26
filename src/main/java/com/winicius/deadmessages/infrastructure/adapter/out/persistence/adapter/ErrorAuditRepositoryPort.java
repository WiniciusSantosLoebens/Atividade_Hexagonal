package com.winicius.deadmessages.infrastructure.adapter.out.persistence.adapter;

import com.winicius.deadmessages.core.model.ErrorAudit;

public interface ErrorAuditRepositoryPort {

    void save(ErrorAudit audit);
}
