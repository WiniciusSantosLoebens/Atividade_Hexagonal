package com.exemplo.auditor.ports.in;

import com.exemplo.auditor.domain.model.DeadLetterMessage;

public interface ProcessDeadLetterUseCase {
    void execute(DeadLetterMessage message, String rawPayload);
}