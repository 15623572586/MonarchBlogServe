package com.Controller;

import com.Service.LoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "login")
    public String login(@RequestBody HashMap<String,String> loginData){
        System.out.println(loginData);
        if (loginData!=null){
            String userId = loginData.get("loginName");
            String password = loginData.get("password");
            if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(password)) {
                return loginService.login(userId, password);
            }
        }
        return "登陆失败";
    }

    @PostMapping(value = "sinup")
    public String sinup(@RequestBody HashMap<String,Object> sinupMap) throws ParseException {
        if (sinupMap!=null && StringUtils.isNotBlank((String) sinupMap.get("userId"))){
            System.out.println(sinupMap);
            String saveStatus = loginService.sinup(sinupMap);
            return saveStatus;
        }
        return "参数缺失，注册失败！";
    }

    @GetMapping(value = "getProvinces")
    public List<HashMap<String,String>> getProvinces(){
        return loginService.getProvinces();
    }
    @GetMapping(value = "getCities")
    public List<HashMap<String,String>> getCities(@RequestParam HashMap<String ,String> provinceIdData){
        if (provinceIdData!=null) {
            String provinceId = provinceIdData.get("provinceId");
            if (StringUtils.isNotBlank(provinceId)) {
                return loginService.getCities(provinceId);
            }
        }
        return null;
    }
}
