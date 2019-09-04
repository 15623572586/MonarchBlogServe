package com.Service;

import com.Dao.CitiesDao;
import com.Dao.ProvincesDao;
import com.Dao.SysUserInfoDao;
import com.Entity.Cities;
import com.Entity.Provinces;
import com.Entity.SysUserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserManageMentService {
    @Autowired
    private SysUserInfoDao sysUserInfoDao;
    @Autowired
    private ProvincesDao provincesDao;
    @Autowired
    private CitiesDao citiesDao;
    @Autowired
    private LoginService loginService;


    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HashMap<String,Object> getUserInfoList() throws ParseException {
        List<SysUserInfo> sysUserInfos = sysUserInfoDao.findAllByDeleteTag("0");
        List<HashMap<String,Object>> allUserInfoList = new ArrayList<>();
        HashMap<String,Object> returnMap = new HashMap<>();
        if (sysUserInfos!=null){
            for (SysUserInfo sysUserInfo:sysUserInfos){
                HashMap<String,Object> userInfo = new HashMap<>();
                if (sysUserInfo!=null && StringUtils.isNotBlank(sysUserInfo.getUserId())){
                    userInfo.put("userId",sysUserInfo.getUserId());
                    userInfo.put("userName",StringUtils.isNotBlank(sysUserInfo.getUserName())?sysUserInfo.getUserName():"--");
                    userInfo.put("realName",StringUtils.isNotBlank(sysUserInfo.getRealName())?sysUserInfo.getRealName():"--");
                    userInfo.put("sex",StringUtils.isNotBlank(sysUserInfo.getUserSex())?sysUserInfo.getUserSex():"--");
                    userInfo.put("createDate",StringUtils.isNotBlank(sysUserInfo.getSinupDate().toString())?dateFormat.parse(sysUserInfo.getSinupDate().toString()):"--");
                    userInfo.put("telNum",StringUtils.isNotBlank(sysUserInfo.getTelNum())?sysUserInfo.getTelNum():"--");
                    String city = "--";
                    if (StringUtils.isNotBlank(sysUserInfo.getUserAdrProv()) && !sysUserInfo.getUserAdrProv().equals("--")){
                        Provinces provinces = provincesDao.findByProvinceId(sysUserInfo.getUserAdrProv());
                        city = provinces.getProvinceName();
                    }
                    if (StringUtils.isNotBlank(sysUserInfo.getUserAdrCity()) && !sysUserInfo.getUserAdrCity().equals("--")){
                        Cities cities = citiesDao.findByCityId(sysUserInfo.getUserAdrCity());
                        city += cities.getCityName();
                    }
                    userInfo.put("city",city);
                    userInfo.put("birthDate",StringUtils.isNotBlank(sysUserInfo.getUserBirthDate().toString())?dateFormat.parse(dateFormat.format(sysUserInfo.getUserBirthDate())):"--");
                    allUserInfoList.add(userInfo);
                }

            }
        }
        if (allUserInfoList!=null && allUserInfoList.size()>0){
            returnMap.put("allUserInfo",allUserInfoList);
        }else {
            returnMap.put("error","未查询到用户信息");
        }
        return returnMap;
    }

    public String deleteUser(String userId){
        SysUserInfo sysUserInfo = sysUserInfoDao.findByUserId(userId);
        sysUserInfo.setDeleteTag("1");
        if (sysUserInfoDao.save(sysUserInfo)!=null){
            return "0";
        }
        return "1";
    }
    public HashMap<String,Object> getOneUserInfo(String userId) throws ParseException {
        HashMap<String,Object> userInfo = new HashMap<>();
        SysUserInfo sysUserInfo = sysUserInfoDao.findByUserId(userId);
        if (sysUserInfo!=null){
            userInfo.put("userId",sysUserInfo.getUserId());
            userInfo.put("password",sysUserInfo.getUserPassword());
            userInfo.put("userName",StringUtils.isNotBlank(sysUserInfo.getUserName())?sysUserInfo.getUserName():"--");
            userInfo.put("realName",StringUtils.isNotBlank(sysUserInfo.getRealName())?sysUserInfo.getRealName():"--");
            userInfo.put("sex",StringUtils.isNotBlank(sysUserInfo.getUserSex())?sysUserInfo.getUserSex():"--");
            userInfo.put("createDate",StringUtils.isNotBlank(sysUserInfo.getSinupDate().toString())?dateFormat.parse(sysUserInfo.getSinupDate().toString()):"--");
            userInfo.put("telNum",StringUtils.isNotBlank(sysUserInfo.getTelNum())?sysUserInfo.getTelNum():"--");
            String province = "--";
            String city = "--";
            if (StringUtils.isNotBlank(sysUserInfo.getUserAdrProv()) && !sysUserInfo.getUserAdrProv().equals("--")){
                Provinces provinces = provincesDao.findByProvinceId(sysUserInfo.getUserAdrProv());
                province = provinces.getProvinceName();
            }
            if (StringUtils.isNotBlank(sysUserInfo.getUserAdrCity()) && !sysUserInfo.getUserAdrCity().equals("--")){
                Cities cities = citiesDao.findByCityId(sysUserInfo.getUserAdrCity());
                city = cities.getCityName();
            }
            userInfo.put("province",province);
            userInfo.put("city",city);
            if (sysUserInfo.getUserBirthDate()!=null && StringUtils.isNotBlank(sysUserInfo.getUserBirthDate().toString())) {
                userInfo.put("birthDate", dateFormat.parse(dateFormat.format(sysUserInfo.getUserBirthDate())));
            }
            List<HashMap<String,String>> Provinces = loginService.getProvinces();
            userInfo.put("Provinces",Provinces);
        }
        if (userInfo==null || userInfo.isEmpty()){
            userInfo.put("error","获取该用户信息失败");
        }
        return userInfo;
    }
}
