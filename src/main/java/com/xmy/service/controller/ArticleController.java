package com.xmy.service.controller;

import com.xmy.bean.bean.Article;
import com.xmy.bean.bean.Comment;
import com.xmy.bean.bean.User;
import com.xmy.bean.common.Page;
import com.xmy.bean.vo.ArticleInfo;
import com.xmy.bean.vo.CommentInfo;
import com.xmy.service.dao.ArticleDao;
import com.xmy.service.dao.CommentDao;
import com.xmy.service.service.ArticleService;
import com.xmy.service.service.CommentService;
import com.xmy.service.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/articleInfoList")
    public List<ArticleInfo> articleInfoList(){
        return articleService.getArticleInfo();
    }

    @RequestMapping("/articleInfoPage")
    public List<ArticleInfo> articleInfoPageList(@RequestBody Page page){
        return articleService.getArticlePageList(page);
    }

    @RequestMapping("/getArticleNum")
    public int getArticleNum(){
        return articleDao.getNum();
    }

    @RequestMapping("/getArticleInfosById")
    public List<ArticleInfo> getArticleInfosById(int id){
        return articleService.getArticleInfosById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/addArticle", produces = "application/json;charset=UTF-8" , method = RequestMethod.POST)
    public JsonResponse addArticle(@RequestBody Article article){
        articleService.addArticle(article);
        return new JsonResponse("");
    }
    @CrossOrigin
    @RequestMapping(value = "/addArticle2", produces = "application/json;charset=UTF-8")
    public JsonResponse addArticle2(@RequestParam String title, @RequestParam String content){
        return new JsonResponse("");
    }

    @CrossOrigin
    @RequestMapping(value = "/commentList", produces = "application/json;charset=UTF-8")
    public JsonResponse commentList(@RequestParam("articleId") String articleId){
        List<CommentInfo> list = articleService.commentList(articleId);
        return new JsonResponse(list);
    }

    @CrossOrigin
    @RequestMapping(value = "/addComment", produces = "application/json;charset=UTF-8")
    public JsonResponse addComment(@RequestParam("content")String commentContent,@RequestParam("userId")String userId,@RequestParam("articleId")String articleId){
        int user_id = Integer.valueOf(userId);
        int article_id = Integer.valueOf(articleId);
        String content = commentContent;
        articleService.addComment(user_id,article_id,content);
        return new JsonResponse("");
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteComment", produces = "application/json;charset=UTF-8")
    public JsonResponse deleteComment(@RequestParam("commentId") String commentId){
        int id = Integer.valueOf(commentId);
        commentService.deleteById(id);
        return new JsonResponse("");
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteArticle", produces = "application/json;charset=UTF-8")
    public JsonResponse deleteArticle(@RequestParam("articleId") String articleId){
        int id = Integer.valueOf(articleId);
        articleService.deleteById(id);
        return new JsonResponse("");
    }

    @CrossOrigin
    @RequestMapping(value = "/laud", produces = "application/json;charset=UTF-8")
    public JsonResponse laud(@RequestParam("articleId") String articleId){
        int id = Integer.valueOf(articleId);
        articleService.giveLaud(id);
        return new JsonResponse("");
    }

}
