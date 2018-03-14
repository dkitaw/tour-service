package com.xmy.service.dao;

import com.xmy.service.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


@Mapper
public interface UserDao {

   @Select("SELECT * FROM user where id = #{id}")
   User getById(@Param("id") Integer id);

	@Select("SELECT * FROM user")
	List<User> userList();




		
}
