package com.xmy.service.controller;

import com.xmy.service.service.ArticleService;
import com.xmy.service.vo.ArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
