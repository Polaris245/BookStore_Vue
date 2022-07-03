package com.example.bookstore.dao;

import com.example.bookstore.pojo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BookDao {
    boolean add(Book book);
    boolean delete(int id);
    Book searchById(int id);
    boolean update(Book book);
    boolean imgUpdate(@Param("id") int id, @Param("imgPath") String imgPath);
    int pageCount(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
    int pageByNameCount(String name);
    List<Book> page(@Param("limit") int Size, @Param("offset") int begin);
    List<Book> pageByName(@Param("limit") int Size, @Param("offset") int begin, @Param("name") String name);
    List<Book> pageByPrice(@Param("limit") int Size, @Param("offset") int begin, @Param("min") BigDecimal min, @Param("max") BigDecimal max);
}
