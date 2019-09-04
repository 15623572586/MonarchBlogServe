package com.Dao;

import com.Entity.SysUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysUserInfoDao extends JpaRepository<SysUserInfo,String> {
    SysUserInfo findByUserId(String userId);
    SysUserInfo findByUserIdAndDeleteTag(String userId,String deleteTag);
    List<SysUserInfo> findAllByDeleteTag(String deleteTag);
}
