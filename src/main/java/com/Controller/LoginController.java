package com.Controller;

import com.Service.LoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "login")
    public String login(@RequestBody HashMap<String,Object> loginData){
        if (loginData!=null){

        }
        return "";
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
}
