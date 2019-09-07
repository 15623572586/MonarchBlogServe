package com.Controller;

import com.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping(value = "saveArticle")
    public String saveArticle(@RequestBody HashMap<String,String> articleData){
        if (articleData!=null){
            return articleService.saveArticle(articleData);
        }
        return "1";
    }
    @GetMapping(value = "getArticleList")
    public HashMap<String,Object> getArticleList(){
        return articleService.getArticleList();
    }
}
