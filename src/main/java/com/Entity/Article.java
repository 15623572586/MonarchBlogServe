package com.Entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "article")
public class Article implements Serializable {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator="jpa-uuid")
    @Column(columnDefinition = "varchar(40) not null comment '文章Id,唯一标识，自动生成'")
    private String articleId;
    @Column(columnDefinition = "varchar(40) not null comment 'userId'")
    private String userId;
    @Column(columnDefinition = "varchar(40) comment '文章标题'")
    private String title;
    @Column(columnDefinition = "varchar(20000) not null comment '文章内容'")
    private String content;
    @Column(columnDefinition = "varchar(6) comment '点赞数量'")
    private String surportCount;
    @Column(columnDefinition = "varchar(6) comment '阅读数量'")
    private String readCount;
    @Column(columnDefinition = "timestamp not null comment '文章发表时间'")
    private Date createTime;
    @Column(columnDefinition = "datetime comment '文章修改时间'")
    private Date modifyTime;
}
