package com.xmy.service.dao;

import com.xmy.service.vo.ArticleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
