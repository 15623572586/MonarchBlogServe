package com.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "sys_user_info")
public class SysUserInfo implements Serializable {
    @Id
    @Column(columnDefinition = "varchar(40) comment '用户Id，用于登陆'")
    private String userId;
    @Column(columnDefinition = "varchar(20) comment '用户昵称，用于显示页面'")
    private String userName;
    @Column(columnDefinition = "varchar(40) comment '登陆密码'")
    private String userPassword;
    @Column(columnDefinition = "varchar(12) comment '用户实名'")
    private String realName;
    @Column(columnDefinition = "varchar(3) comment '用户性别'")
    private String userSex;
    @Column(columnDefinition = "date comment '用户生日'")
    private Date userBirthDate;
    @Column(columnDefinition = "varchar(3) comment '用户地址省份'")
    private String userAdrProv;
    @Column(columnDefinition = "varchar(3) comment '用户地址城市'")
    private String userAdrCity;
    @Column(columnDefinition = "varchar(11) comment '用户电话号码'")
    private String telNum;
    @Column(columnDefinition = "date comment '注册日期'")
    private Date sinupDate;
    @Column(columnDefinition = "date comment '修改日期'")
    private Date modifyDate;

}
