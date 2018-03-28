package com.xmy.service.dao;

import com.xmy.bean.vo.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description:
 * @Author: xumengyang
 * @Date: Created in 9:54 2018/3/23
 */
@Mapper
public interface CommentDao {
    @Select("select a.id,a.content,a.time,a.article_id articleId,a.user_id userId,b.nickname,b.headPic from comment a, user b where a.user_id = b.id and article_id = #{articleId}")
    List<CommentInfo> getByArticleId(Integer articleId);
}
