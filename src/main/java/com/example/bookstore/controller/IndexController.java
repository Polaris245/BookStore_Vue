package com.example.bookstore.controller;

import com.example.bookstore.pojo.Cart;
import com.example.bookstore.pojo.Page;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * 联系作者
 * CSDN：https://blog.csdn.net/qq_46037812?spm=1000.2115.3001.5343
 * 个人博客：blog.wanfeng.site
 */

@RestController
public class IndexController {

    @Autowired
    BookService bookService;

    @GetMapping("/index")
    public Map<String, Object> indexPage(String pageNo, HttpSession session){
        Map<String, Object> result = new HashMap<>();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null)
            cart = new Cart();
        session.setAttribute("cart", cart);
        if (pageNo == null) pageNo = "1";
        Page page = bookService.page(pageNo);
        if (page != null) {
            page.setUrl("/index?");
            result.put("page", page);
            result.put("result", true);
        }
        else
            result.put("result", false);
        result.put("cart", cart);
        return result;
    }

    @GetMapping("/index/pageByPrice")
    public Map<String, Object> pageByPrice(@RequestParam Map<String, String> map) {
        Map<String, Object> result = new HashMap<>();
        Page page = bookService.pageByPrice(map);
        if (page != null) {
            page.setUrl("/index/pageByPrice?min=" + map.get("min") + "&max=" + map.get("max") + "&");
            result.put("page", page);
            result.put("result", true);
        }
        else
            result.put("result", false);
        return result;
    }

    @GetMapping("/index/pageByName")
    public Map<String, Object> pageByName(@RequestParam Map<String, String> map) {
        Map<String, Object> result = new HashMap<>();
        Page page = bookService.pageByName(map.get("pageNo"), map.get("name"));
        if (page != null) {
            page.setUrl("/index/pageByName?name=" + map.get("name") + "&");
            result.put("page", page);
            result.put("result", true);
        }
        else
            result.put("result", false);
        return result;
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
