package com.xmy.service.controller;

import com.xmy.service.service.ArticleService;
import com.xmy.service.util.JsonResponse;
import com.xmy.service.vo.ArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: xumengyang
 * @Date: Created in 11:50 2018/3/23
 */
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/articleInfoList")
    public List<ArticleInfo> articleInfoList(){
        return articleService.getArticleInfo();
    }

    @CrossOrigin
    @RequestMapping(value = "/addArticle", produces = "application/json;charset=UTF-8")
    public JsonResponse addArticle(HttpServletRequest request){
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        return new JsonResponse("");
    }
    @CrossOrigin
    @RequestMapping(value = "/addArticle2", produces = "application/json;charset=UTF-8")
    public JsonResponse addArticle2(@RequestParam String title, @RequestParam String content){
        return new JsonResponse("");
    }
}
