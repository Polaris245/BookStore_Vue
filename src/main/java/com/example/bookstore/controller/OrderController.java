package com.example.bookstore.controller;
import com.example.bookstore.pojo.OrderItem;
import com.example.bookstore.pojo.Page;
import com.example.bookstore.pojo.User;
import com.example.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/order")
    public Map<String, Object> order(String pageNo, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        if (Helper.userVerify(result, user, false)) {
            if (pageNo == null) pageNo = "1";
            Page page = orderService.page(pageNo, user.getUsername());
            if (page != null) {
                page.setUrl("/order?");
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

    @GetMapping("/order/detail")
    public Map<String, Object> detail(@RequestParam("id") String orderId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        if (Helper.userVerify(result, user, false)) {
            List<OrderItem> orderItems = orderService.searchAll(orderId);
            int status = orderService.getStatus(orderId);
            result.put("status", status);
            result.put("result", true);
            result.put("orderItems", orderItems);
        }
        return result;
    }

    @GetMapping("/order/delete")
    public Map<String, Object> delete(@RequestParam("id") String orderId, @RequestParam("pageNo") String pageNo, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        if (Helper.userVerify(result, user, false)) {
            if (pageNo == null) pageNo = "1";
            if (orderService.delete(orderId)) {
                Page page = orderService.page(pageNo, user.getUsername());
                if (page != null) {
                    page.setUrl("/order?");
                    result.put("result", true);
                    result.put("page", page);
                } else {
                    result.put("result", false);
                    result.put("msg", "删除成功，但查询发生了未知错误");
                }
            }
            else {
                result.put("result", false);
                result.put("msg", "更新失败");
            }
        }
        return result;
    }

    @GetMapping("/order/update")
    public Map<String, Object> update(@RequestParam("status") String status, @RequestParam("id") String orderId, @RequestParam("pageNo") String pageNo, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        if (Helper.userVerify(result, user, false)) {
            if (pageNo == null) pageNo = "1";
            if (orderService.updateStatus(orderId, status)) {
                Page page = orderService.page(pageNo, user.getUsername());
                if (page != null) {
                    page.setUrl("/order?");
                    result.put("result", true);
                    result.put("page", page);
                } else {
                    result.put("result", false);
                    result.put("msg", "更新成功，但查询发生了未知错误");
                }
            }
            else {
                result.put("result", false);
                result.put("msg", "更新失败");
            }
        }
        return result;
    }

    @GetMapping("/manager/order")
    public Map<String, Object> managerOrder(String pageNo, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (Helper.userVerify(result, user, true)) {
            if (pageNo == null) pageNo = "1";
            Page page = orderService.pageAll(pageNo);
            if (page != null) {
                page.setUrl("/manager/order?");
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
}
