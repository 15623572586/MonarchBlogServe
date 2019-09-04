package com.Dao;

import com.Entity.Provinces;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvincesDao extends JpaRepository<Provinces,String> {
    Provinces findByProvinceId(String provinceId);
}
