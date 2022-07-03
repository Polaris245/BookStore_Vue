package com.example.bookstore.controller;

import com.example.bookstore.pojo.Book;
import com.example.bookstore.pojo.Cart;
import com.example.bookstore.pojo.CartItem;
import com.example.bookstore.pojo.User;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CartController {

    @Autowired
    BookService bookService;
    @Autowired
    OrderService orderService;

    @GetMapping("/cart")
    public Map<String, Object> cart(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Cart cart = (Cart) session.getAttribute("cart");
        result.put("cart", cart);
        return result;
    }

    @GetMapping("/cart/add")
    public Map<String, Object> add(String id, HttpSession session) {
        Book book = bookService.searchById(id);
        Map<String,Object> result = new HashMap<>();
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice(), book.getImgPath());
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.add(cartItem);
            cart.setLastName(cartItem.getName());
            result.put("result", true);
            result.put("cart", cart);
        }
        else
            result.put("result", false);
        return result;
    }

    @GetMapping("/cart/delete")
    public Map<String, Object> delete(@RequestParam("id") String itemId, HttpSession session) {
        Map<String,Object> result = new HashMap<>();
        try {
            int id = Integer.parseInt(itemId);
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                cart.delete(id);
                result.put("result", true);
                result.put("cart", cart);
            }
            else
                result.put("result", false);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            return result;
        }
    }

    @GetMapping("/cart/clear")
    public Map<String, Object> clear(HttpSession session) {
        Map<String,Object> result = new HashMap<>();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.clear();
            result.put("result", true);
            result.put("cart", cart);
        }
        else
            result.put("result", false);
        return result;
    }

    @GetMapping("/cart/update")
    public Map<String, Object> update(@RequestParam("id") String itemId, @RequestParam("count") String itemCount, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            try {
                int id = Integer.parseInt(itemId);
                int count = Integer.parseInt(itemCount);
                if (count < 1) {
                    cart.delete(id);
                    result.put("result", true);
                    result.put("cart", cart);
                    return result;
                }
                Book book = bookService.searchById(itemId);
                if (book.getStock() >= count) {
                    cart.updateCount(id, count);
                    result.put("result", true);
                    result.put("cart", cart);
                }
                else {
                    result.put("result", false);
                    result.put("msg", "修改失败，商品库存不足");
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                result.put("result", false);
                return result;
            }
        }
        else {
            result.put("result", false);
            return result;
        }
    }

    @GetMapping("/cart/checkout")
    public Map<String, Object> checkout(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        if (cart != null) {
            String orderId = null;
            if (user == null) {
                result.put("result", false);
                result.put("msg", "结算失败，请先登录");
                return result;
            }
            String username = user.getUsername();
            orderId = orderService.createOrder(cart, username);
            if (orderId != null) {
                result.put("result", true);
                result.put("cart", cart);
                result.put("orderId", orderId);
            }
            else {
                result.put("result", false);
                result.put("msg", "下单失败，商品库存不足");
            }
        }
        else {
            result.put("result", false);
            result.put("msg", "结算失败，未知错误");
        }
        return result;
    }
}
