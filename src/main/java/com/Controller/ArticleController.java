package com.Controller;

import com.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.net.ftp.FtpProtocolException;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * @PostMapping(value = "imgUpload")
     * public Map<String ,Object> imgUpload(@RequestBody HttpServletRequest httpServletRequest) throws IOException {
     *上面那种接收 图片的的方式会报错 不支持 。。。。
     * @param httpServletRequest
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "imgUpload" )
    @ResponseBody
    public Map<String ,Object> imgUpload(HttpServletRequest httpServletRequest) throws IOException, FtpProtocolException {
        Map<String, Object> uploadImgStatusMap = new HashMap<>();
        if (httpServletRequest!=null) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
            MultipartFile file = multipartHttpServletRequest.getFile("img");
            if (file!=null) {
                uploadImgStatusMap = articleService.imgUpload(file);
            }else {
                uploadImgStatusMap.put("code", 202);
                uploadImgStatusMap.put("msg", "上传失败");
            }
        }else {
            uploadImgStatusMap.put("code", 202);
            uploadImgStatusMap.put("msg", "上传失败");
        }
        return uploadImgStatusMap;
    }
}
