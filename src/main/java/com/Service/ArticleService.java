package com.Service;

import com.Dao.ArticleDao;
import com.Entity.Article;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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


    public HashMap<String,Object> getArticleList(){
        HashMap<String,Object> articleMap = new HashMap<>();
        List<HashMap<String,Object>> articleList = new ArrayList<>();
        List<Article> articles = articleDao.findAll();
        Integer total = 0;
        if (articles!=null && articles.size()>0){
            for (Article article : articles) {
                if (article!=null && StringUtils.isNotBlank(article.getArticleId())){
                    HashMap<String,Object> articleData = new HashMap<>();
                    articleData.put("articleId",article.getArticleId());
                    articleData.put("userId",article.getUserId());
                    articleData.put("title",StringUtils.isNotBlank(article.getTitle())?article.getTitle():"");
                    articleData.put("content",article.getContent());
                    articleData.put("commentCount",article.getCommentCount());
                    articleData.put("readCount",article.getReadCount());
                    articleData.put("createTime",timeFormat.format(article.getCreateTime()));
                    articleList.add(articleData);
                    total++;
                }
            }
            articleMap.put("articleList",articleList);
            articleMap.put("total",total);
        }else {
            articleMap.put("error","查询错误，未查询到随笔记录！");
        }
        return articleMap;
    }
}
