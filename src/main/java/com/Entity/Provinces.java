package com.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "provinces")
public class Provinces {
    @Id
    private String Id;
    private String provinceName;
    private String provinceId;
}
