package com.wpj.paper.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {

    @Id
    private Long id;

    private Long userId;
    private Long cash;
    private Long credit;
    private Long debt;
    private Long sourceId;
    private Long cashBalance;
    private Long creditBalance;
    private Date createTime;
    private int type;
}
