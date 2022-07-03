package com.example.bookstore.controller;

import com.example.bookstore.pojo.Page;
import com.example.bookstore.pojo.User;
import com.example.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserManagerController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public Map<String, Object> pageAll(String pageNo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (Helper.userVerify(result, user, true)) {
            if (pageNo == null) pageNo = "1";
            Page page = userService.page(pageNo);
            if (page != null) {
                page.setUrl("/user?");
                result.put("result", true);
                result.put("page", page);
                user.setPassword(null);
                result.put("user", user);
            }
            else {
                result.put("result", false);
                result.put("msg", "未知错误");
            }
        }
        return result;
    }

    @GetMapping("/user/delete")
    public Map<String, Object> delete(@RequestParam("id") String username, @RequestParam("pageNo") String pageNo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (Helper.userVerify(result, user, true)) {
            if (userService.delete(username)) {
                Page page = userService.page(pageNo);
                if (page != null) {
                    page.setUrl("/user?");
                    result.put("result", true);
                    result.put("page", page);
                }
                else {
                    result.put("result", false);
                    result.put("msg", "删除成功，但查询发生错误");
                }
            }
            else {
                result.put("result", false);
                result.put("msg", "删除失败");
            }
        }
        return result;
    }

    @GetMapping("/user/change")
    public Map<String, Object> change(@RequestParam("id") String username, @RequestParam("pageNo") String pageNo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (Helper.userVerify(result, user, true)) {
            User user1 = userService.searchByUsername(username);
            if (user1.isAdmin()) {
                if (userService.setAdmin(false,username)) {
                    Page page = userService.page(pageNo);
                    if (page != null) {
                        page.setUrl("/user?");
                        result.put("result", true);
                        result.put("page", page);
                    }
                    else {
                        result.put("result", false);
                        result.put("msg", "更新成功，但查询发生错误");
                    }
                }
                else {
                    result.put("result", false);
                    result.put("msg", "更新失败");
                }
            }
            else {
                if (userService.setAdmin(true,username)) {
                    Page page = userService.page(pageNo);
                    if (page != null) {
                        page.setUrl("/user?");
                        result.put("result", true);
                        result.put("page", page);
                    }
                    else {
                        result.put("result", false);
                        result.put("msg", "更新成功，但查询发生错误");
                    }
                }
                else {
                    result.put("result", false);
                    result.put("msg", "更新失败");
                }
            }
        }
        return result;
    }

    @RequestMapping("/user/userinfo")
    public Map<String, Object> userinfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        user = userService.searchByUsername(user.getUsername());
        if (user != null) {
            result.put("result", true);
            result.put("user", user);
        }
        else
            result.put("result", false);
        return result;
    }

    @PostMapping("/user/update")
    public Map<String, Object> update(@RequestParam("username") String username,@RequestParam("email")  String email
            , @RequestParam("password") String password, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        if (userService.update(password, email, username)) {
            session.invalidate();
            result.put("result", true);
        }
        else
            result.put("result", false);
        return result;
    }
}
