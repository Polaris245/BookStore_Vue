package com.example.bookstore.controller;

import com.example.bookstore.pojo.Book;
import com.example.bookstore.pojo.Page;
import com.example.bookstore.pojo.User;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/manager/book")
    public Map<String, Object> pageAll(String pageNo, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        if (Helper.userVerify(result, user, true)) {
            if (pageNo == null) pageNo = "1";
            Page page = bookService.page(pageNo);
            if (page != null) {
                page.setUrl("/manager/book?");
                result.put("result", true);
                result.put("page", page);
            }
            else {
                result.put("result", false);
                result.put("msg", "未知错误");
            }
        }
        return result;
    }

    @GetMapping("/manager/book/delete")
    public Map<String, Object> delete(String pageNo, String id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        if (Helper.userVerify(result, user, true)) {
            if (pageNo == null) pageNo = "1";
            if (bookService.delete(id)) {
                Page page = bookService.page(pageNo);
                if (page != null) {
                    page.setUrl("/manager/book?");
                    result.put("result", true);
                    result.put("page", page);
                } else {
                    result.put("result", false);
                    result.put("msg", "删除成功，但查询时发生了未知错误");
                }
            } else {
                result.put("result", false);
                result.put("msg", "删除失败");
            }
        }
        return result;
    }

    @GetMapping("/manager/book/detail")
    public Map<String, Object> detail(String id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        if (Helper.userVerify(result, user, true)) {
            Book book = bookService.searchById(id);
            if (book != null) {
                result.put("result", true);
                result.put("book", book);
            }
            else {
                result.put("result", false);
                result.put("msg", "查询失败");
            }
        }
        return result;
    }

    @GetMapping("/book/detail")
    public Map<String, Object> showDetail(String id, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Book book = bookService.searchById(id);
        if (book != null) {
            result.put("result", true);
            result.put("book", book);
        }
        else {
            result.put("result", false);
            result.put("msg", "查询失败");
        }
        return result;
    }

    @PostMapping(("/manager/book/edit"))
    public Map<String, Object> edit(@RequestParam("name") String name, @RequestParam("author") String author,
                                    @RequestParam("price") String price, @RequestParam("sales") String sales, @RequestParam("stock") String stock,
                                    @RequestParam("id") String id, @RequestParam(value = "imgPath", required = false) String imgPath , @RequestParam(value = "file", required = false)MultipartFile file, HttpSession session) throws FileNotFoundException {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        Book book;
        if (Helper.userVerify(result, user, true)) {
            map.put("name", name);
            map.put("author", author);
            map.put("price", price);
            map.put("sales", sales);
            map.put("stock", stock);
            if (file == null) {
                map.put("imgPath", imgPath);
            }
            else {
                String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/uploads";
                File fileDir = new File(filePath);
                if (!fileDir.exists()) fileDir.mkdir();
                long timestamp = System.currentTimeMillis();
                filePath = filePath + "\\" + timestamp + ".jpg";
                String relativePath = "/uploads/" + timestamp + ".jpg";
                File uploadFile = new File(filePath);
                try {
                    file.transferTo(uploadFile);
                    map.put("imgPath", relativePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    result.put("result", false);
                    result.put("msg", "文件保存时出现异常");
                    return result;
                }
            }
            if (id.equals("new")) {
                book = bookService.add(map);
            }
            else {
                map.put("id", id);
                book = bookService.update(map);
            }
            if (book != null) {
                result.put("result", true);
                result.put("book", book);
            } else {
                result.put("result", false);
                result.put("msg", "添加失败，未知错误");
            }
        }
        return result;
    }
}
