package com.xmy.service.dao;


import com.xmy.bean.bean.Plate;
import com.xmy.bean.bean.User;
import com.xmy.bean.vo.Ipinfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


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

    @UpdateProvider(type=SqlProvider.class, method="updateInfo")
    int update(@Param("id")Integer id,
               @Param("nickname")String nickname,
               @Param("sex")Integer sex,
               @Param("email")String email,
               @Param("phone")String phone);

    @Select("select id, nickname, headPic, backgroundPic, backbackPic,backgroundAlt from user where id<6")
    List<User> getAdverts();

    @Insert("insert into ip (userId,ipAddr,time) values(#{userId},#{ip},now())")
    void saveIp(@Param("ip") String ip, @Param("userId")Integer userId);

    @Select("select user.id userId,nickname,ipAddr,time from user,ip where user.id=ip.userId")
    List<Ipinfo> loginIpInfo();

    @Update("update user set state = '1' where id = #{id}")
    int startUser(@Param("id") Integer id);

    @Update("update user set state= '0' where id = #{id}")
    int stopUser(@Param("id") Integer id);

    @Select("select * from plate")
    List<Plate> getPlates();

    @Insert("insert into user (nickname,username,password,sex) values(#{pd.nickname},#{pd.username},#{pd.password},#{pd.sex})")
    int save(@Param("pd") User user);

    @Select("select * from user where username = #{username}")
    User getByUsername(@Param("username") String username);
}
