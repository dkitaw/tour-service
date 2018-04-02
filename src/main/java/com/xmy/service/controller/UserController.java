package com.xmy.service.controller;

import com.xmy.bean.bean.User;
import com.xmy.service.dao.UserDao;
import com.xmy.service.util.JsonResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public User login(@RequestParam String username, @RequestParam String password){
        return userDao.getByUsernameAndPassword(username, password);
    }

    @RequestMapping("/userList")
    public Object userList(){
        return new JsonResponse(userDao.userList());
    }

    @RequestMapping("/list")
    public List<User> list(){
        return userDao.userList();
    }

    @RequestMapping("/getUserById")
    public User getById(@RequestParam("id") int id){
        return userDao.getById(id);
    }

    //关注
    @RequestMapping(value = "/follow", produces = "application/json;charset=UTF-8")
    public JsonResponse follow(@RequestParam("user1Id") String user1_id, @RequestParam("user2Id") String user2_id){
        int user1Id = Integer.valueOf(user1_id);
        int user2Id = Integer.valueOf(user2_id);
        userDao.addFollow(user1Id, user2Id);
        return new JsonResponse("");
    }
}
