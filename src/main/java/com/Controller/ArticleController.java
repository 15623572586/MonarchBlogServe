package com.Controller;

import com.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public Map<String ,Object> imgUpload(HttpServletRequest httpServletRequest) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;

        MultipartFile file=multipartHttpServletRequest.getFile("img");
        String fileName =  file.getOriginalFilename();
        fileName = fileName.replace(" ","_");
        System.out.println(fileName);
//        FileOutputStream fos = new FileOutputStream(new File(fileName));
//        file.transferTo(new File("http:///49.235.111.233/image/"+fileName));
//        FileInputStream fs = (FileInputStream) file.getInputStream();
//        byte[] buffer = new byte[1024];
//        int len = 0;
//        while ((len = fs.read(buffer)) != -1) {
//            fos.write(buffer, 0, len);
//        }
//        fos.close();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "上传成功");
        map.put("url", "http://49.235.111.233/image/01.jpg");
        return map;
    }
}
