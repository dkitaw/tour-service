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




		
}
