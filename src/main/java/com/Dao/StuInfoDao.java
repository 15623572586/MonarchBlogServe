package com.Dao;

import com.Entity.StuInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  StuInfoDao extends JpaRepository<StuInfo,String> {
}
