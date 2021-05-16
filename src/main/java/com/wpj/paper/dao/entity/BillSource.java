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

}
