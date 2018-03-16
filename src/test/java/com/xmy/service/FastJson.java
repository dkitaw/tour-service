package com.xmy.service;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;



class FastJsonTest {
    public static void main(String[] args) {
        // 构建用户geust
        User guestUser = new User();
        guestUser.setName("guest");
        guestUser.setAge(28);
        // 构建用户root
        User rootUser = new User();
        rootUser.setName("root");
        guestUser.setAge(35);
        // 构建用户组对象
        UserGroup group = new UserGroup();
        group.setName("admin");
        group.getUsers().add(guestUser);
        group.getUsers().add(rootUser);
        // 用户组对象转JSON串
        String jsonString = JSON.toJSONString(group);
        System.out.println("jsonString:" + jsonString);
        // JSON串转用户组对象
        UserGroup group2 = JSON.parseObject(jsonString, new UserGroup().getClass());
        JsonResponse<ArrayList<User>> jr = JSON.parseObject(jsonString, new JsonResponse<ArrayList<User>>().getClass());
        JsonResponse2<User> jr2 = JSON.parseObject(jsonString, new JsonResponse2<User>().getClass());


       /* Type type = new ParameterizedTypeImpl(JsonResponse.class, new Class[] { new JsonResponse<ArrayList<User>>().getClass() });
        Type type2 = new ParameterizedTypeImpl(JsonResponse.class, new Class[] { new JsonResponse2<User>().getClass() });
        JsonResponse<ArrayList<User>> jr5 = JSON.parseObject(jsonString, type);
        JsonResponse2<User> jr6 = JSON.parseObject(jsonString, type2);*/


        JsonResponse<ArrayList<User>> jr3 = JSON.parseObject(jsonString,new TypeReference<JsonResponse<ArrayList<User>>>(){});
        JsonResponse2<User> jr4 = JSON.parseObject(jsonString, new TypeReference<JsonResponse2<User>>(){});

        System.out.println("group2:" + group2);

        /*// 构建用户对象数组
        User[] users = new User[2];
        users[0] = guestUser;
        users[1] = rootUser;
        // 用户对象数组转JSON串
        String jsonString2 = JSON.toJSONString(users);
        System.out.println("jsonString2:" + jsonString2);
        // JSON串转用户对象列表
        List<User> users2 = JSON.parseArray(jsonString2, User.class);
        System.out.println("users2:" + users2);  */
    }
}


class User {
    private String name;  
    private int age;  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public int getAge() {  
        return age;  
    }  
  
    public void setAge(int age) {  
        this.age = age;  
    }  
  
    @Override  
    public String toString() {  
        return "User [name=" + name + ", age=" + age + "]";  
    }  
};  
  
class UserGroup {  
    private String name;  
    private List<User> users = new ArrayList<User>();  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public List<User> getUsers() {  
        return users;  
    }  
  
    public void setUsers(List<User> users) {  
        this.users = users;  
    }  
  
    @Override  
    public String toString() {  
        return "UserGroup [name=" + name + ", users=" + users + "]";  
    }  
}  

class JsonResponse<T>{
    private String name;
    private T users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getUsers() {
        return users;
    }

    public void setUsers(T users) {
        this.users = users;
    }
}
class JsonResponse2<T>{
    private String name;
    private List<T> users = new ArrayList<T>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getUsers() {
        return users;
    }

    public void setUsers(List<T> users) {
        this.users = users;
    }
}
class ParameterizedTypeImpl implements ParameterizedType {
    private final Class  raw;
    private final Type[] args;
    public ParameterizedTypeImpl(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }
    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }
    @Override
    public Type getRawType() {
        return raw;
    }
    @Override
    public Type getOwnerType() {
        return null;
    }

}


