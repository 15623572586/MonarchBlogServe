package com.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 记录用户点赞的文章
 */
@Entity
@Data
@Table(name = "statistic_surport")
public class StatisticSurport implements Serializable {
    @Id
    @Column(columnDefinition = "varchar(40) not null comment '用户Id'")
    private String userId;
    @Id
    @Column(columnDefinition = "varchar(40) not null comment '文章Id'")
    private String articleId;
}
