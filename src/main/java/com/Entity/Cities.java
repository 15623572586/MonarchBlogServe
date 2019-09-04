package com.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cities")
public class Cities implements Serializable {
    @Id
    private String Id;
    private String cityId;
    private String cityName;
    private String provinceId;
}
