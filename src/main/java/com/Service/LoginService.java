package com.Service;

import com.Dao.SysUserInfoDao;
import com.Entity.SysUserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@Service
public class LoginService {
    @Autowired
    private SysUserInfoDao sysUserInfoDao;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String sinup(HashMap<String,Object> sinupMap) throws ParseException {
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId((String) sinupMap.get("userId"));
        sysUserInfo.setUserName(sinupMap.get("userName").toString());
        sysUserInfo.setUserPassword(sinupMap.get("userPassword").toString());
        if (StringUtils.isNotBlank(sinupMap.get("telNum").toString())){
            sysUserInfo.setTelNum(sinupMap.get("telNum").toString());
        }
        if (StringUtils.isNotBlank(sinupMap.get("realName").toString())){
            sysUserInfo.setRealName(sinupMap.get("realName").toString());
        }
        if (StringUtils.isNotBlank(sinupMap.get("userSex").toString())){
            sysUserInfo.setUserSex(sinupMap.get("userSex").toString());
        }
        if (StringUtils.isNotBlank(sinupMap.get("userBirthDate").toString())){
            sysUserInfo.setUserBirthDate(simpleDateFormat.parse(sinupMap.get("userBirthDate").toString()));
        }
        if (StringUtils.isNotBlank(sinupMap.get("userAdrProv").toString())){
            sysUserInfo.setUserAdrProv(sinupMap.get("userAdrProv").toString());
        }
        if (StringUtils.isNotBlank(sinupMap.get("userAdrCity").toString())){
            sysUserInfo.setUserAdrCity(sinupMap.get("userAdrCity").toString());
        }
        if (sysUserInfoDao.save(sysUserInfo)!=null){
            return "0";
        }
        return "注册失败！";
    }
}
