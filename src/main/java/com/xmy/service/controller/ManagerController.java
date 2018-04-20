package com.xmy.service.controller;

import com.xmy.bean.bean.Comment;
import com.xmy.bean.common.Page;
import com.xmy.bean.vo.*;
import com.xmy.service.dao.ArticleDao;
import com.xmy.service.dao.ChatDao;
import com.xmy.service.dao.CommentDao;
import com.xmy.service.dao.UserDao;
import com.xmy.service.service.ArticleService;
import com.xmy.service.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xmy on 2018/4/16.
 */
@RestController
@RequestMapping("/manage")
public class ManagerController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ChatDao chatDao;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/getArticles",produces={"application/json;charset=UTF-8"})
    public JsonResponse getList(@RequestBody ArticleSearch as){
        int totalNum = articleService.getCount(as);
        int pageSize = as.getPageSize();
        int currentPage = as.getCurrentPage();
        Page p = new Page(pageSize,totalNum, currentPage);
        int totalPage = p.getTotalPage();
        //List<ArticleInfo> list = articleDao.getPageList(p);
        as.setCurrentResult((currentPage-1)*pageSize);
        List<ArticleInfo> list = articleService.getArticleBySearch(as);
        Map<String,Object> map = new HashMap<>();
        map.put("totalPage",totalPage);
        map.put("list",list);
        return new JsonResponse(map);
    }
/*
@CrossOrigin(origins = "*")
    @RequestMapping("/getArticles")
    public JsonResponse getList(@RequestParam("page")int page, @RequestParam("pageSize")int pageSize){
        int totalNum = articleDao.getNum();
        Page p = new Page(pageSize,totalNum,page);
        int totalPage = p.getTotalPage();
        List<ArticleInfo> list = articleDao.getPageList(p);
        Map<String,Object> map = new HashMap<>();
        map.put("totalPage",totalPage);
        map.put("list",list);
        return new JsonResponse(map);
    }
*/

    @CrossOrigin(origins = "*")
    @RequestMapping("/commentList")
    public JsonResponse commentList(){
        List<CommentInfo> list = commentDao.getList();
        return new JsonResponse(list);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/userlist")
    public JsonResponse userlist(){
        return new JsonResponse(userDao.userList());
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/loginIpinfo")
    public JsonResponse loginIpinfo(){
        List<Ipinfo> list = userDao.loginIpInfo();
        return new JsonResponse(list);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/chatInfolist")
    public JsonResponse chatInfolist(){
        List<ChatlogVo> list = chatDao.getList();
        return new JsonResponse(list);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/startUser")
    public JsonResponse startUser(@RequestParam("userId") Integer id){
        userDao.startUser(id);
        return new JsonResponse("");
    }
    @CrossOrigin(origins = "*")
    @RequestMapping("/stopUser")
    public JsonResponse stopUser(@RequestParam("userId") Integer id){
        userDao.stopUser(id);
        return new JsonResponse("");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/login")
    public JsonResponse login(HttpServletRequest req){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(username!=""&&password!=""){
            req.getSession().setAttribute("username",username);
        }
        return new JsonResponse("");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/checkLogin")
    public JsonResponse checkLogin(HttpSession session){
        if(null!=session.getAttribute("username")){
            return new JsonResponse("1");
        }
        return new JsonResponse("1");
    }

}
