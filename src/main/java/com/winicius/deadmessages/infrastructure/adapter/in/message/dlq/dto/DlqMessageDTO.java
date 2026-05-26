package com.winicius.deadmessages.infrastructure.adapter.in.message.dlq.dto;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class DlqMessageDTO {

    private String zipCode;

    private Long customerId;

    private List<OrderItemDTO> orderItems;

    private String origin;

    private Instant occurredAt;
}