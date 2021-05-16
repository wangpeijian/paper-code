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

    private Long order_id;
    private Long userId;
    private Long service_id;
    private Long price;
    private Long num;

}
