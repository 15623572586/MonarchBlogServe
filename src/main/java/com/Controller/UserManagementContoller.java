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

    /**
     * 获取一个用户的信息
     * @param userIdData
     * @return
     * @throws ParseException
     */
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

    /**
     * 点击查看文章时左侧栏显示笔者相关信息
     * @param userIdMap
     * @return
     */
    @GetMapping(value = "getUserInfoAndArticle")
    public HashMap<String,Object> getUserInfoAndArticle(@RequestParam HashMap<String,String> userIdMap){
        HashMap<String,Object> personalInfoAndArticleMap = new HashMap<>();
        if (userIdMap!=null) {
            if (StringUtils.isNotBlank(userIdMap.get("userId"))) {
                personalInfoAndArticleMap = userManageMentService.getUserInfoAndArticle(userIdMap.get("userId"));
            }else {
                personalInfoAndArticleMap.put("error","用户ID获取失败：未获取到用户ID！");
            }
        }else {
            personalInfoAndArticleMap.put("error","参数获取失败：服务器未获取到查询参数！");
        }
        return personalInfoAndArticleMap;
    }
}
