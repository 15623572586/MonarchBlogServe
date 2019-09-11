package com.Entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "image_url")
public class ImageUrl {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator="jpa-uuid")
    @Column(columnDefinition = "varchar(40) not null comment '图片Id,唯一标识，自动生成'")
    private String uuid;
    @Column(columnDefinition = "varchar(100) not null comment '图片在服务器的地址'")
    private String imageUrl;
}
