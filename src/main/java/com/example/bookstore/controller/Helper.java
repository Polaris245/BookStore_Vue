package com.example.bookstore.controller;

import com.example.bookstore.pojo.User;

import java.util.Map;

public class Helper {

    public static boolean userVerify(Map<String, Object> map, User user, boolean isAdmin) {
        if (isAdmin) {
            if (user != null) {
                if (user.isAdmin()) return true;
                else {
                    map.put("result", false);
                    map.put("msg", "非管理员禁止访问");
                    return false;
                }
            }
            else {
                map.put("result", false);
                map.put("msg", "请先登录");
                return false;
            }
        }
        else {
            if (user != null) return true;
            else {
                map.put("result", false);
                map.put("msg", "请先登录");
                return false;
            }
        }
    }
}
