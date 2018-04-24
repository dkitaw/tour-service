package com.xmy.service.dao;

import com.xmy.bean.bean.Article;
import com.xmy.bean.common.Page;
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
    List<ArticleInfo> getArticleInfo();

    @SelectProvider(type = SqlProvider.class, method = "getArticleInfo")
    List<ArticleInfo> getArticleBySearch(@Param("currentResult") Integer currentResult,
                                     @Param("pageSize") Integer pageSize,
                                     @Param("nowdays") String nowdays,
                                     @Param("plate") Integer plate,
                                     @Param("approve") Integer approve);

    @InsertProvider(type=SqlProvider.class, method="insertArticle")
    void insert(@Param("title") String title,
                @Param("content") String content,
                @Param("pics") String pics,
                @Param("address") String address,
                @Param("plate") Integer plate,
                @Param("userId") Integer userId);

    @Select("select a.id,a.title,a.content,a.pics,a.create_time createTime, a.address,a.zanNum,a.plate,a.user_id userId,b.nickname,b.headPic,b.sex from article a, user b where a.user_id = b.id and b.id = #{id}")
    List<ArticleInfo> getArticleInfoById(@Param("id") int id);
    @Select("select a.id,a.title,a.content,a.pics,a.create_time createTime, a.address,a.zanNum,a.plate,a.user_id userId,b.nickname,b.headPic,b.sex from article a, user b where a.user_id = b.id and a.id = #{id}")
    ArticleInfo getArticleDetail(@Param("id") int id);

    @Delete("delete from article where id = #{id}")
    int deleteById(@Param("id") int id);

    //点赞
    @Update("update article set zanNum = zanNum+1 where id = #{id}")
    int giveLaud(@Param("id") int id);

    @Select("select a.id,a.title,a.content,a.pics,a.create_time createTime, a.address,a.zanNum,a.plate,a.user_id userId,b.nickname,b.headPic,b.sex from article a, user b where a.user_id = b.id limit #{pojo.currentResult},#{pojo.pageSize}")
    List<ArticleInfo> getPageList(@Param("pojo") Page page);

    @Select("select count(1) from article")
    int getNum();

    @SelectProvider(type = SqlProvider.class, method = "getCount")
    int getCount(@Param("nowdays") String nowdays,
                 @Param("plate") Integer plate,
                 @Param("approve") Integer approve);
}
