package com.Controller;

import com.Service.UserManageMentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;

@RestController
public class UserManagementContoller {

    @Autowired
    private UserManageMentService userManageMentService;

    @GetMapping(value = "getUserInfoList")
    public HashMap<String,Object>getUserInfoList() throws ParseException {
        return userManageMentService.getUserInfoList();
    }
    @PostMapping(value = "deleteUser")
    public String deleteUser(@RequestBody HashMap<String,String> userIdMap){
        if (userIdMap!=null && !userIdMap.isEmpty()){
            String userId = userIdMap.get("userId");
            return userManageMentService.deleteUser(userId);
        }else {
            return "1";
        }
    }
    @GetMapping(value = "getOneUserInfo")
    public HashMap<String,Object> getOneUserInfo(@RequestParam HashMap<String,String> userIdData) throws ParseException {
        HashMap<String,Object> returnMap = new HashMap<>();
        if (userIdData!=null){
            String userId = userIdData.get("userId");
            if (StringUtils.isNotBlank(userId)) {
                returnMap = userManageMentService.getOneUserInfo(userId);
            }
        }else {
            returnMap.put("error","获取该用户信息失败");
        }
        return returnMap;
    }
}
