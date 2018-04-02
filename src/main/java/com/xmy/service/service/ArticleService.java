package com.xmy.service.service;

import com.xmy.bean.bean.Article;
import com.xmy.bean.bean.Comment;
import com.xmy.bean.bean.User;
import com.xmy.bean.common.Page;
import com.xmy.bean.vo.ArticleInfo;
import com.xmy.bean.vo.CommentInfo;
import com.xmy.service.dao.ArticleDao;
import com.xmy.service.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: xumengyang
 * @Date: Created in 9:49 2018/3/23
 */
@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CommentDao commentDao;


    public List<ArticleInfo> getArticleInfo(){
        List<ArticleInfo> list = articleDao.getArticleInfo();
        for(ArticleInfo info: list){
            int articleId = info.getId();
            List<CommentInfo> commentList = commentDao.getByArticleId(articleId);

            info.setCommentInfoList(commentList);
        }
        return list;
    }

    public void addArticle(Article article) {
        articleDao.insert(article.getTitle(),article.getContent(),article.getPics(),article.getAddress(),article.getPlate(),article.getUserId());
    }

    public List<CommentInfo> commentList(String articleId) {
        int id = Integer.valueOf(articleId);
        return commentDao.getByArticleId(id);
    }

    public int addComment(int user_id, int article_id, String content) {
        return commentDao.insert(user_id,article_id,content);
    }

    public List<ArticleInfo> getArticleInfosById(int id) {
        return articleDao.getArticleInfoById(id);
    }

    public int deleteById(int id) {
        return articleDao.deleteById(id);
    }

    public int giveLaud(int id) {
        return articleDao.giveLaud(id);
    }

    public List<ArticleInfo> getArticlePageList(Page page) {
        return articleDao.getPageList(page);
    }
}
