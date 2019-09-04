package com.Dao;

import com.Entity.Cities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitiesDao extends JpaRepository<Cities,String> {
    List<Cities> findAllByProvinceId(String provinceId);
    Cities findByCityId(String cityId);
}
