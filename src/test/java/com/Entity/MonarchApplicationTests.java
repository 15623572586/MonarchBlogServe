package com.Entity;

import com.Dao.SysUserInfoDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonarchApplicationTests {
    @Autowired
    SysUserInfoDao sysUserInfoDao;

    @Test
    public void contextLoads() {
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setRealName("喻云虎");
        sysUserInfo.setTelNum("15623572586");
        sysUserInfo.setUserAdrCity("湖北");
        sysUserInfo.setUserAdrCity("武汉");
        sysUserInfo.setUserId("15623572586");
        sysUserInfo.setUserName("Monarch");
        sysUserInfo.setUserBirthDate(new Date("1998-02-25"));
        sysUserInfo.setUserSex("男");
        sysUserInfoDao.save(sysUserInfo);
    }

}
