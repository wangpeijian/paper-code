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

    @Id
    private Long id;
    private Long orderId;
    private Long userId;
    private Long price;
    private Long statusCode;
    public OrderSource(Long id, Long userId, Long orderId, Long price) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.price = price;
        this.statusCode = 0L;
    }
}
