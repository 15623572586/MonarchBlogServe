package com.Dao;

import com.Entity.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserInfoDao extends JpaRepository<SysUserInfo,String> {
}
