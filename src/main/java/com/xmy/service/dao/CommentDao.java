package com.xmy.service.dao;

import com.xmy.bean.bean.Comment;
import com.xmy.bean.vo.CommentInfo;
import org.apache.ibatis.annotations.*;
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

    @Insert("insert into comment (content,article_id,user_id,time) values (#{content},#{articleId},#{userId},now())")
    int insert(@Param("userId") int userId, @Param("articleId") int articleId, @Param("content") String content);

    @Delete("delete from comment where id = #{id}")
    int deleteById(@Param("id") int id);
}
