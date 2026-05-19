package com.exemplo.auditor.adapters.in.sqs.dto;

import com.exemplo.auditor.domain.model.DeadLetterMessage;
import java.util.List;
import java.util.stream.Collectors;

public class SqsMessageDto {
    public String zipCode;
    public Long customerId;
    public List<ItemDto> orderItems;
    public String origin;
    public String occurredAt;

    public static class ItemDto {
        public Long sku;
        public int amount;
    }

    public DeadLetterMessage toDomain() {
        DeadLetterMessage msg = new DeadLetterMessage();
        msg.setZipCode(this.zipCode);
        msg.setCustomerId(this.customerId);
        msg.setOrigin(this.origin);
        msg.setOccurredAt(this.occurredAt);

        if (this.orderItems != null) {
            List<DeadLetterMessage.Item> domainItems = this.orderItems.stream().map(i -> {
                DeadLetterMessage.Item item = new DeadLetterMessage.Item();
                item.setSku(i.sku);
                item.setAmount(i.amount);
                return item;
            }).collect(Collectors.toList());
            msg.setOrderItems(domainItems);
        }
        return msg;
    }
}