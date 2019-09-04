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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    private SysUserInfoDao sysUserInfoDao;
    @Autowired
    private ProvincesDao provincesDao;
    @Autowired
    private CitiesDao citiesDao;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HashMap<String,Object> login(String userId , String password){
        SysUserInfo sysUserInfo = sysUserInfoDao.findByUserIdAndDeleteTag(userId,"0");
        HashMap<String,Object> returnMap = new HashMap<>();
        if (sysUserInfo==null){
            returnMap.put("error","该用户不存在");
        }
        if (password.equals(sysUserInfo.getUserPassword())){
            HashMap userInfo = new HashMap();
            userInfo.put("userId", userId);
            userInfo.put("userName",sysUserInfo.getUserName());
            returnMap.put("userInfo",userInfo);
        }else {
            returnMap.put("error","密码错误");
        }
        return returnMap;
    }

    public String sinup(HashMap<String,Object> sinupMap) throws ParseException {
        SysUserInfo sysUserInfo;
        String action = (String) sinupMap.get("action");
        if (action.equals("sinup")) {
            sysUserInfo = sysUserInfoDao.findByUserId((String) sinupMap.get("userId"));
            if (sysUserInfo != null) {
                return "该用户名已被注册，请重新输入！";
            }
        }
        sysUserInfo = new SysUserInfo();
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
            sysUserInfo.setUserBirthDate(dateFormat.parse(sinupMap.get("userBirthDate").toString()));
        }
        if (StringUtils.isNotBlank(sinupMap.get("userAdrProv").toString())){
            sysUserInfo.setUserAdrProv(sinupMap.get("userAdrProv").toString());
        }
        if (StringUtils.isNotBlank(sinupMap.get("userAdrCity").toString())){
            sysUserInfo.setUserAdrCity(sinupMap.get("userAdrCity").toString());
        }
        sysUserInfo.setDeleteTag("0");
        sysUserInfo.setSinupDate(new Date());
        if (sysUserInfoDao.save(sysUserInfo)!=null){
            return "0";
        }
        return "注册失败！";
    }

    public List<HashMap<String,String>> getProvinces() {
        List<Provinces> provinceList = provincesDao.findAll();
        List<HashMap<String,String>> provinces = new ArrayList<>();
        for (Provinces province : provinceList){
            HashMap<String,String> province1 = new HashMap<>();
            province1.put("provinceId",province.getProvinceId());
            province1.put("provinceName",province.getProvinceName());
            provinces.add(province1);
        }
        return provinces;
    }
    public List<HashMap<String,String>> getCities(String provinceId){
        List<Cities> citiesList = citiesDao.findAllByProvinceId(provinceId);
        List<HashMap<String,String >> cities = new ArrayList<>();
        if (citiesList!=null && citiesList.size()>0){
            for (Cities city : citiesList){
                HashMap<String,String> city1 = new HashMap<>();
                city1.put("cityId",city.getCityId());
                city1.put("cityName",city.getCityName());
                cities.add(city1);
            }
            return cities;
        }
        return null;
    }
}
