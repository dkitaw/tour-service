package com.xmy.service.dao;

import com.xmy.bean.vo.ChatTableVo;
import com.xmy.bean.vo.ChatlogVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: xumengyang
 * @Date: Created in 9:49 2018/4/12
 */
@Mapper
public interface ChatDao {

    @Select("select fromId,nickname fromNickname,min(state) as state from chatlog, user where toId=#{toId} and fromId=user.id group by fromId;")
    List<ChatTableVo> getListGroupByFromId(@Param("toId") int toId);

    @Select("select fromId,nickname fromnick,headPic frompic,content,sendTime,state from chatlog,user where fromId = user.id and ((toId=#{toId} and fromId=#{fromId}) or (toId=#{fromId} and fromId=#{toId})) order by sendTime asc;")
    List<ChatlogVo> getChatLog(@Param("toId") Integer toId, @Param("fromId") Integer fromId);

    @Insert("insert into chatlog (fromId,toId,content,sendTime,state) values(#{fromId},#{toId},#{content},now(),#{state})")
    int save(@Param("fromId") Integer fromId,@Param("toId") Integer toId,@Param("content") String content,@Param("state") Integer state);

    @Update("update chatlog set state=1 where fromId=#{fromId} and toId=#{toId}")
    int updateState(@Param("toId") Integer toId,@Param("fromId") Integer fromId);
}
