package com.xmy.service.dao;


import com.xmy.bean.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


@Mapper
public interface UserDao {

   @Select("SELECT * FROM user where id = #{id}")
   User getById(@Param("id") Integer id);

   @Select("select * from user where username = #{username} and password = #{password}")
   User getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	@Select("SELECT * FROM user")
	List<User> userList();

    @Insert("insert into follow (user1_id, user2_id) values(#{user1Id}, #{user2Id})")
    int addFollow(@Param("user1Id") int user1Id, @Param("user2Id") int user2Id);

}
