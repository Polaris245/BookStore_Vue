package com.example.bookstore.service;

import com.example.bookstore.dao.BookDao;
import com.example.bookstore.pojo.Book;
import com.example.bookstore.pojo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    public Book add(Map<String, String> map){
        if (Helper.isLegal(map)) {
            try {
                BigDecimal price = new BigDecimal(map.get("price"));
                int sales = Integer.parseInt(map.get("sales"));
                int stock = Integer.parseInt(map.get("stock"));
                Book book = new Book(null, map.get("name"), map.get("author"), price, sales, stock, map.get("imgPath"));
                if (bookDao.add(book)) {
                    book = bookDao.searchById(book.getId());
                    return book;
                }
                else
                    return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public boolean delete(String bookId){
        try {
            int id = Integer.parseInt(bookId);
            return bookDao.delete(id);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Book update(Map<String, String> map) {
        if (Helper.isLegal(map)) {
            try {
                int id = Integer.parseInt(map.get("id"));
                BigDecimal price = new BigDecimal(map.get("price"));
                int sales = Integer.parseInt(map.get("sales"));
                int stock = Integer.parseInt(map.get("stock"));
                if (bookDao.update(new Book(id, map.get("name"), map.get("author"), price, sales, stock, map.get("imgPath"))))
                    return bookDao.searchById(id);
                else
                    return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public boolean imgUpdate(Map<String, String> map) {
        if (Helper.isLegal(map)) {
            try {
                int id = Integer.parseInt(map.get("id"));
                return bookDao.imgUpdate(id, map.get("imgPath"));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public Page page(String no) {
        Page page = new Page();
        int pageTotalCount = bookDao.pageCount(new BigDecimal(0), new BigDecimal(Double.MAX_VALUE));
        Integer begin = Helper.pageHelper(page, pageTotalCount, no);
        if (begin == null) return null;
        List<Book> books = bookDao.page(page.getPageSize(), begin);
        page.setItems(books);
        return page;
    }

    public Page pageByName(String no, String name) {
        Page page = new Page();
        int pageTotalCount = bookDao.pageByNameCount(name);
        Integer begin = Helper.pageHelper(page, pageTotalCount, no);
        if (begin == null) return null;
        List<Book> books = bookDao.pageByName(page.getPageSize(), begin, name);
        page.setItems(books);
        return page;
    }

    public Page pageByPrice(Map<String, String> map) {
        Page page = new Page();
        BigDecimal min = new BigDecimal(0), max = new BigDecimal(Double.MAX_VALUE);
        int pageTotalCount;
        try {
            if (!map.get("min").equals(""))
                min = new BigDecimal(map.get("min"));
            if (!map.get("max").equals(""))
                max = new BigDecimal(map.get("max"));
            pageTotalCount = bookDao.pageCount(min, max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Integer begin = Helper.pageHelper(page, pageTotalCount, map.get("pageNo"));
        if (begin == null) return null;
        List<Book> books = bookDao.pageByPrice(page.getPageSize(), begin, min, max);
        page.setItems(books);
        return page;
    }

    public Book searchById(String bookId) {
        int id;
        try {
            id = Integer.parseInt(bookId);
            return bookDao.searchById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
