package com.winicius.deadmessages.infrastructure.adapter.in.message.dlq.listener;

import org.springframework.stereotype.Component;

import com.winicius.deadmessages.application.service.DlqAuditService;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DlqConsumerSqsAdapter {

    private final DlqAuditService service;

    @SqsListener("${queue.order-events}")
    public void receive(String payload){

        service.process(payload);
    }
}