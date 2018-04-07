package com.xmy.service.service;

import com.xmy.bean.bean.User;
import com.xmy.service.dao.UserDao;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @Author: xumengyang
 * @Date: Created in 13:47 2018/4/2
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public int saveInfo(HttpServletRequest req) {
        String user_id = req.getParameter("userId").toString();
        int userId = Integer.valueOf(user_id);
        String nickname = req.getParameter("nickname").toString();
        String sexStr = req.getParameter("sex").toString();
        int sex = Integer.valueOf(sexStr);
        String email = req.getParameter("email").toString();
        String phone = req.getParameter("phone").toString();

        return userDao.update(userId,nickname,sex,email,phone);
    }
}
