package com.xmy.service.controller;

import com.alibaba.fastjson.JSON;
import com.xmy.bean.bean.Plate;
import com.xmy.bean.bean.User;
import com.xmy.service.dao.UserDao;
import com.xmy.service.service.UserService;
import com.xmy.service.util.JsonResponse;
import com.xmy.service.util.MD5;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password,HttpServletRequest request){
        try {
            String ip = getIpAddress(request);
            User user =  userDao.getByUsernameAndPassword(username, MD5.md5(password));
            userDao.saveIp(ip,user.getId());
            return user;
        } catch (Exception e) {
            return null;
        }
    }
    @CrossOrigin(origins = "*")
    @RequestMapping("/register")
    public JsonResponse register(@RequestBody User user){
        User u = userDao.getByUsername(user.getUsername());
        if(null==u) {
            user.setPassword(MD5.md5(user.getPassword()));
            userDao.save(user);
        }else{
            return new JsonResponse(new Exception());
        }
        return new JsonResponse("");
    }

    @RequestMapping("/userList")
    public Object userList(){
        return new JsonResponse(userDao.userList());
    }

    @RequestMapping("/getAdverts")
    public List<User> getAdverts(){
        return userDao.getAdverts();
    }

    @RequestMapping("/getPlates")
    public List<Plate> getPlates(){
        return userDao.getPlates();
    }

    @RequestMapping("/list")
    public List<User> list(){
        return userDao.userList();
    }

    @RequestMapping("/getUserById")
    public User getById(@RequestParam("id") int id){
        return userDao.getById(id);
    }

    @CrossOrigin
    @RequestMapping("/saveInfo")
    public JsonResponse saveInfo(HttpServletRequest req){
        userService.saveInfo(req);


        return new JsonResponse("");
    }

    //关注
    @RequestMapping(value = "/follow", produces = "application/json;charset=UTF-8")
    public JsonResponse follow(@RequestParam("user1Id") String user1_id, @RequestParam("user2Id") String user2_id){
        int user1Id = Integer.valueOf(user1_id);
        int user2Id = Integer.valueOf(user2_id);
        userDao.addFollow(user1Id, user2Id);
        return new JsonResponse("");
    }

    public static String getIpAddress(javax.servlet.http.HttpServletRequest request) {
        /*String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;*/
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }
}
