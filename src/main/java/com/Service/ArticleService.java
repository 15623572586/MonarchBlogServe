package com.Service;

import com.Dao.ArticleDao;
import com.Entity.Article;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    public String saveArticle(HashMap<String,String> articleData){
        String userId = articleData.get("userId");
        String title = articleData.get("title");
        String content = articleData.get("content");
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(content)){
            Article article = new Article();
            article.setArticleId(article.getArticleId());
            article.setUserId(userId);
            if (StringUtils.isNotBlank(title)){
                article.setTitle(title);
            }
            article.setContent(content);
//            article.setCreateTime(article.getCreateTime());
            if (articleDao.save(article)!=null){
                return "0";
            }
        }
        return "1";
    }
}
