package com.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "provinces")
public class Provinces implements Serializable {
    @Id
    private String Id;
    private String provinceName;
    private String provinceId;
}
