package com.exemplo.auditor.domain.model;

import java.util.List;

public class DeadLetterMessage {
    private String zipCode;
    private Long customerId;
    private List<Item> orderItems;
    private String origin;
    private String occurredAt;


    public static class Item {
        private Long sku;
        private int amount;

        public int getAmount() { return amount; }
        public void setAmount(int amount) { this.amount = amount; }

        public Long getSku(){
            return sku;
        }
        public void setSku(Long sku){
            this.sku = sku;
        }
    }

    public int getTotalAmount() {
        if (orderItems == null) return 0;
        return orderItems.stream().mapToInt(Item::getAmount).sum();
    }
    public String getZipCode(){
        return zipCode;
    }
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
    public Long getCustomerId(){
        return customerId;
    }
    public void setCustomerId(Long customerId){
        this.customerId = customerId;
    }
    public List<Item> getOrderItems(){
        return orderItems;
    }
    public void setOrderItems(List<Item> orderItems){
        this.orderItems = orderItems;
    }
    public String getOrigin(){
        return origin;
    }
    public void setOrigin(String origin){
        this.origin = origin;
    }
    public String getOccurredAt(){
        return occurredAt;
    }
    public void setOccurredAt(String occurredAt){
        this.occurredAt = occurredAt;
    }

}