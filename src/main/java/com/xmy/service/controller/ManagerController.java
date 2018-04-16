package com.xmy.service.controller;

import com.xmy.service.dao.ArticleDao;
import com.xmy.service.dao.CommentDao;
import com.xmy.service.dao.UserDao;
import com.xmy.service.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by xmy on 2018/4/16.
 */
@RestController("/manage")
public class ManagerController {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentDao commentDao;

    @CrossOrigin(origins = "*")
    @RequestMapping("/getArticles")
    public JsonResponse getList(@RequestParam("page")int page, @RequestParam("count")int count){
        return new JsonResponse("");
    }

}
