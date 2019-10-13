package com.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
class StatisticSurportKey {
    private String userId;
    private String articleId;
}
/**
 * 记录用户点赞的文章
 */
@Entity
@Data
@Table(name = "statistic_surport")
@IdClass(StatisticSurport.class)
public class StatisticSurport implements Serializable {
    @Id
    @Column(columnDefinition = "varchar(40) not null comment '用户Id'")
    private String userId;
    @Id
    @Column(columnDefinition = "varchar(40) not null comment '文章Id'")
    private String articleId;
}
