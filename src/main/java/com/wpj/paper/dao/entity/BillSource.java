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
public class BillSource {

    @Id
    private Long id;
    private Long userId;
    private Long amount;
    private Long statusCode;
    private Long billId;
    public BillSource(Long id, Long userId, Long amount, Long billId) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.billId = billId;
        this.statusCode = 0L;
    }

}
