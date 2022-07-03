package com.example.bookstore.dao;

import com.example.bookstore.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemDao {

    boolean add(OrderItem orderItem);
    boolean delete(String orderId);
    int pageCount(String orderId);
    List<OrderItem> page(@Param("orderId") String orderId, @Param("offset") int begin, @Param("limit") int size);
    List<OrderItem> searchAll(String orderId);
}
