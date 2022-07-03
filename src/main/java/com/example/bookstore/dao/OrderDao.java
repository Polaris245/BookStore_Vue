package com.example.bookstore.dao;

import com.example.bookstore.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {

    boolean add(Order order);
    boolean delete(String orderId);
    int pageCount(String username);
    List<Order> page(@Param("username") String username, @Param("offset") int begin, @Param("limit") int size);
    boolean updateStatus(@Param("orderId") String orderId, @Param("status") int status);
    int pageAllCount();
    List<Order> pageAll(@Param("offset") int begin, @Param("limit") int size);
    int getStatus(String orderId);
}
