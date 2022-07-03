package com.example.bookstore.service;

import com.example.bookstore.dao.UserDao;
import com.example.bookstore.pojo.Book;
import com.example.bookstore.pojo.Page;
import com.example.bookstore.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User signIn(Map<String, String> map) {
        if (Helper.isLegal(map))
            return userDao.signIn(map.get("username"), map.get("password"));
        else
            return null;
    }

    public User signUp(Map<String, String> map) {
        if (Helper.isLegal(map))
        {
            User user = new User(map.get("username"), map.get("password"), map.get("email"), false);
            if (userDao.signUp(user))
                return user;
            else
                return null;
        }
        else
            return null;
    }

    public boolean existUser(String username) {
        if (username != null && !username.equals(""))
            return userDao.existUser(username) != 0;
        else
            return true;
    }

    public boolean delete(String username) {
        if (username != null)
            return userDao.delete(username);
        else
            return false;
    }

    public boolean setAdmin(boolean value, String username) {
        try {
            return userDao.setAdmin(value, username);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String password, String email, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("email", email);
        map.put("password", password);
        if (Helper.isLegal(map))
            return userDao.update(map);
        else
            return false;
    }

    public Page page(String no) {
        Page page = new Page();
        int pageTotalCount = userDao.pageCount();
        Integer begin = Helper.pageHelper(page, pageTotalCount, no);
        if (begin == null) return null;
        List<User> users = userDao.page(begin, page.getPageSize());
        page.setItems(users);
        return page;
    }
    public User searchByUsername(String username) {
        return userDao.searchByUsername(username);
    }
}
