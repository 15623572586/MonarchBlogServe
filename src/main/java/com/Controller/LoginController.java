package com.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LoginController {

    @PostMapping(value = "login")
    public String login(@RequestBody HashMap<String,Object> loginData){
        if (loginData!=null){
            System.out.println(loginData);
        }
        return "";
    }
}
