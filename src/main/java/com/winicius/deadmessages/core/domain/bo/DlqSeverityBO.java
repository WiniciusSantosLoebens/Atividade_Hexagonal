package com.winicius.deadmessages.core.domain.bo;

import java.util.List;

import com.winicius.deadmessages.core.model.Severity;
import com.winicius.deadmessages.infrastructure.adapter.in.message.dlq.dto.OrderItemDTO;

public class DlqSeverityBO {

    public Severity calculate(List<OrderItemDTO> items){

        int total = items.stream()
                .mapToInt(OrderItemDTO::getAmount)
                .sum();

        if(total > 100){
            return Severity.HIGH;
        }

        if(total >= 50){
            return Severity.MEDIUM;
        }

        return Severity.LOW;
    }
}
