package com.xmy.service.dao;

import com.xmy.bean.bean.Article;
import com.xmy.bean.vo.ArticleInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description:
 * @Author: xumengyang
 * @Date: Created in 9:25 2018/3/23
 */
@Mapper
public interface ArticleDao {

    @Select("select a.id,a.title,a.content,a.pics,a.create_time createTime, a.address,a.zanNum,a.plate,a.user_id userId,b.nickname,b.headPic,b.sex from article a, user b where a.user_id = b.id")
    public List<ArticleInfo> getArticleInfo();

    @InsertProvider(type=SqlProvider.class, method="insertArticle")
    void insert(@Param("title") String title,
                @Param("content") String content,
                @Param("pics") String pics,
                @Param("address") String address,
                @Param("plate") Integer plate,
                @Param("userId") Integer userId);

    @Select("select a.id,a.title,a.content,a.pics,a.create_time createTime, a.address,a.zanNum,a.plate,a.user_id userId,b.nickname,b.headPic,b.sex from article a, user b where a.user_id = b.id and b.id = #{id}")
    List<ArticleInfo> getArticleInfoById(@Param("id") int id);

    @Delete("delete from article where id = #{id}")
    int deleteById(@Param("id") int id);
}
