package com.wpj.paper.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSource {

    public OrderSource(Long id, Long userId, Long orderId, Long serviceId, Long price, Long num){
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.serviceId = serviceId;
        this.price = price;
        this.num = num ;
        this.statusCode = 0L;
    }

    @Id
    private Long id;

    private Long orderId;
    private Long userId;
    private Long serviceId;
    private Long price;
    private Long num;
    private Long statusCode;
}
