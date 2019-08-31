package com.Entity;

import com.Dao.StuInfoDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonarchApplicationTests {
    @Autowired
    StuInfoDao stuInfoDao;

    @Test
    public void contextLoads() {
        List<StuInfo> testListSave = new ArrayList<>();
        StuInfo stuInfo = new StuInfo();
        String stuId = "201713137018";
        String stuName = "喻云虎";
        String stuSex = "男";
        String stuTelNum = "15623572586";
        stuInfo.setStuId(stuId);
        stuInfo.setStuName(stuName);
        stuInfo.setStuSex(stuSex);
        stuInfo.setStuTelNum(stuTelNum);
        testListSave.add(stuInfo);


        stuInfo = new StuInfo();
        stuInfo.setStuId("201713137015");
        stuInfo.setStuName("刘威");
        stuInfo.setStuSex("男");
        stuInfo.setStuTelNum("15623572586");

        testListSave.add(stuInfo);

        stuInfoDao.saveAll(testListSave);
        System.out.println(stuInfoDao.findAll());
    }

}
