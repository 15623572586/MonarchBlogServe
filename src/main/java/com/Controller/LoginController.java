package com.Controller;

import com.Service.LoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     *
     * @param loginData
     * @return
     */
    @PostMapping(value = "login")
    public HashMap<String,Object> login(@RequestBody HashMap<String,String> loginData,HttpServletRequest httpServletRequest){
        HashMap<String,Object> loginStatus = new HashMap<>();
        if (loginData!=null){
            String userId = loginData.get("loginName");
            String password = loginData.get("password");
            if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(password)) {
                loginStatus = loginService.login(userId, password);
                if (loginStatus.get("error")==null || StringUtils.isBlank(loginStatus.get("error").toString())) {
                    HttpSession httpSession = httpServletRequest.getSession(true); //新建session对象
                    httpSession.setAttribute("userId", userId);
                }
                return loginStatus;
            }
        }else {
            loginStatus.put("error","参数缺失");
        }
        return loginStatus;
    }

    @PostMapping(value = "sinup")
    public String sinup(@RequestBody HashMap<String,Object> sinupMap) throws ParseException {
        if (sinupMap!=null && StringUtils.isNotBlank((String) sinupMap.get("userId"))){
            System.out.println(sinupMap);
            String saveStatus = loginService.sinupOrModify(sinupMap);
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
