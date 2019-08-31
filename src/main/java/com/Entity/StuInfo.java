package com.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 测试实体类
 *
 */

@Entity
@Table(name = "stu_info")
@Data
public class StuInfo implements Serializable {
    @Id
    private String stuId;
    private String stuName;
    private String stuSex;
    @Column(columnDefinition = "varchar(11) comment '电话号码'")
    private String stuTelNum;
}
