package com.Service;

import com.Dao.ArticleDao;
import com.Dao.ImageUrlDao;
import com.Dao.StatisticSurportDao;
import com.Entity.Article;
import com.Entity.ImageUrl;
import com.Entity.StatisticSurport;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.net.ftp.FtpProtocolException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ImageUrlDao imageUrlDao;
    @Autowired
    private StatisticSurportDao statisticSurportDao;

    @Value("${web.upload.path}")
    private String uploadPath;
    @Value("${imgUrl}")
    private String imgUrl;

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
            article.setReadCount("0");
            article.setSurportCount("0");
//            article.setCreateTime(article.getCreateTime());
            if (articleDao.save(article)!=null){
                return "0";
            }
        }
        return "1";
    }


    public HashMap<String,Object> getArticleList(HashMap<String,String> userIdMap){
        HashMap<String,Object> articleMap = new HashMap<>();
        List<HashMap<String,Object>> articleList = new ArrayList<>();
        List<Article> articles = new ArrayList<>();

        if (userIdMap==null || userIdMap.isEmpty()) {
             articles = articleDao.findAll();
        }else {
            articles = articleDao.findAllByUserIdOrderByCreateTimeDesc(userIdMap.get("userId"));
        }
        Integer total = 0;
        if (articles!=null && articles.size()>0){
            for (Article article : articles) {
                if (article!=null && StringUtils.isNotBlank(article.getArticleId())){
                    HashMap<String,Object> articleData = new HashMap<>();
                    articleData.put("articleId",article.getArticleId());
                    articleData.put("userId",article.getUserId());
                    articleData.put("title",StringUtils.isNotBlank(article.getTitle())?article.getTitle():"");
                    articleData.put("content",article.getContent());
                    articleData.put("surportCount",article.getSurportCount());
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

    /**
     * 更新统计阅读数量
     * @param articleId
     * @return
     */
    public HashMap<String,String> modifyReadCount(String articleId){
        HashMap<String,String> returnMap = new HashMap<>();
        Article article = articleDao.findByArticleId(articleId);
        Boolean status = false;
        Integer count = 0;
        if(article!=null){
            count = Integer.parseInt(article.getReadCount())+1;
            article.setReadCount(count.toString());
            status = articleDao.save(article)!=null;
        }else {
            returnMap.put("status","1");
            returnMap.put("msg","查询文章失败");
        }
        if (status){
            returnMap.put("status","0");
            returnMap.put("readCount",count.toString());
        }else {
            returnMap.put("status","1");
            returnMap.put("msg","更新文章阅读次数失败");
        }
        return returnMap;
    }

    /**
     * 文章点赞统计
     * @param surportMap
     * @return
     */
    public HashMap<String,String> surportArticle(HashMap<String,String> surportMap){
        HashMap<String,String> returnMap = new HashMap<>();
        Boolean isNotNull = surportMap!=null;
        if (isNotNull){
            String userId = surportMap.get("userId");
            String articleId = surportMap.get("articleId");
            if (StringUtils.isNotBlank(userId)&& StringUtils.isNotBlank(articleId)){
                StatisticSurport statisticSurport = statisticSurportDao.findAllByArticleIdAndUserId(articleId,userId);
                if (statisticSurport==null) {
                    Article article = articleDao.findByArticleId(articleId);
                    if (article != null) {
                        Integer surportCount = Integer.parseInt(article.getSurportCount()) + 1;
                        article.setSurportCount(surportCount.toString());
                        if (articleDao.save(article) != null) {
                            statisticSurport = new StatisticSurport();
                            statisticSurport.setArticleId(articleId);
                            statisticSurport.setUserId(userId);
                            if (statisticSurportDao.save(statisticSurport) != null) {
                                returnMap.put("status", "0");
                                returnMap.put("surportCount", surportCount.toString());
                                returnMap.put("msg", "点赞成功");
                            } else {
                                returnMap.put("status", "1");
                                returnMap.put("msg", "点赞失败");
                            }
                        } else {
                            returnMap.put("status", "1");
                            returnMap.put("msg", "点赞失败");
                        }
                    } else {
                        returnMap.put("status", "1");
                        returnMap.put("msg", "查询文章失败");
                    }
                }else {
                    returnMap.put("status","1");
                    returnMap.put("msg","您很欣赏这篇文章，不能重复点赞哦！");
                }
            }else {
                returnMap.put("status","1");
                returnMap.put("msg","用户名或文章获取失败");
            }
        }
        return returnMap;
    }

    /**
     * 上传图片到图片服务器，并返回图片在服务器的地址
     * @param file
     * @return
     * @throws IOException
     */
    public Map<String,Object> imgUpload(MultipartFile file) throws IOException, FtpProtocolException {
        Map<String, Object> uploadImgStatusMap = new HashMap<>();

        String fileName = file.getOriginalFilename();
        String filename1 = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID()+filename1;
        fileName = fileName.replace("-", "");
        File newFile = new File(uploadPath + fileName);
        FileOutputStream fos = new FileOutputStream(newFile);
        FileInputStream fs = (FileInputStream) file.getInputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fs.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        fs.close();
        uploadImgStatusMap.put("code", 200);
        uploadImgStatusMap.put("msg", "上传成功");
        uploadImgStatusMap.put("url", imgUrl + fileName);
        //将上传到服务器的图片地址保存到数据库，方便管理
        ImageUrl imageUrl = new ImageUrl();
        imageUrl.setUuid(imageUrl.getUuid());
        imageUrl.setImageUrl(imgUrl+fileName);
        imageUrlDao.save(imageUrl);
        return uploadImgStatusMap;
    }
}
