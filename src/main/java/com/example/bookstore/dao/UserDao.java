package com.example.bookstore.dao;

import com.example.bookstore.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    User signIn(@Param("username")String username, @Param("password")String password);
    boolean signUp(User user);
    int existUser(String username);
    boolean delete(String username);
    boolean setAdmin(@Param("status")boolean status, @Param("username") String username);
    boolean update(Map<String, String> map);
    int pageCount();
    List<User> page(@Param("offset") int begin, @Param("limit") int size);
    User searchByUsername(String username);
}
