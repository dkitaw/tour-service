package com.xmy.service.service;

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
}
