package com.xmy.service.controller;

import com.xmy.service.dao.UserDao;
import com.xmy.service.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: xumengyang
 * @Date: Created in 16:04 2018/2/27
 */
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public String login(){
        return "登录成功";
    }

    @RequestMapping("/userList")
    public Object userList(){
        return new JsonResponse(userDao.userList());
    }

}
