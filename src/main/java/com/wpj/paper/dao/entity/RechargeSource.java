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
public class RechargeSource {

    @Id
    private Long id;
    private Long userId;
    private Long amount;
    private Long rechargeId;
    private Long statusCode;
    public RechargeSource(Long id, Long userId, Long amount, Long rechargeId) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.rechargeId = rechargeId;
        this.statusCode = 0L;
    }
}
