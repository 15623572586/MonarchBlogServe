package com.Service;

import com.Dao.ArticleDao;
import com.Dao.ImageUrlDao;
import com.Entity.Article;
import com.Entity.ImageUrl;
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
