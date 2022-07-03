package com.example.bookstore.controller;

import com.example.bookstore.pojo.User;
import com.example.bookstore.service.UserService;
import com.google.code.kaptcha.Producer;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@Controller
@RequestMapping("/login")
public class UserController {

    @Autowired
    private Producer captchaProducer;
    @Autowired
    private UserService userService;

    @PostMapping("/isLogin")
    @ResponseBody
    public Map<String, Object> isLogin(HttpSession session){
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            result.put("result", true);
            result.put("user", user);
        }
        else {
            result.put("result", false);
        }
        return result;
    }

    @PostMapping("/signIn")
    @ResponseBody
    public Map<String, Object> login(@RequestBody Map<String, String> map, HttpSession session){
        User user = userService.signIn(map);
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            session.setAttribute("user", user);
            result.put("result", true);
            result.put("msg", "登录成功");
            result.put("user", user.getUsername());
            return result;
        }
        else if (!userService.existUser(map.get("username"))) {
            result.put("result", false);
            result.put("msg", "用户不存在，请先注册");
            return result;
        }
        else {
            result.put("result", false);
            result.put("msg", "用户名或密码错误");
            return result;
        }
    }

    @PostMapping("/signUp")
    @ResponseBody
    public Map<String, Object> signUp(@RequestBody Map<String, String> map, HttpSession session){
        String token = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        Map<String, Object> result = new HashMap<>();
        if (!token.equals(map.get("code"))) {
            result.put("result", false);
            result.put("msg", "注册失败，验证码错误");
            return result;
        }
        User user = userService.signUp(map);
        if (user != null) {
            session.setAttribute("user", user);
            result.put("result", true);
        }
        else {
            result.put("result", false);
            result.put("msg", "注册失败，未知错误");
        }
        return result;
    }

    @GetMapping("/existUser")
    @ResponseBody
    public Map<String, Object> exitUser(String username){
        Map<String, Object> map = new HashMap<>();
        map.put("result", userService.existUser(username));
        return map;
    }

    @GetMapping("/codeVerify")
    @ResponseBody
    public Map<String, Object> codeVerify(HttpSession session, @RequestParam(value = "code")String code){
        String token = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        Map<String,Object> result = new HashMap<>();
        if (code.equals(token))
            result.put("result",true);
        else {
            result.put("result", false);
            result.put("msg", "验证码错误");
        }
        return result;
    }

    @RequestMapping("/kaptcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        session.setAttribute(KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @RequestMapping("/isSignIn")
    @ResponseBody
    public Map<String, Object> isSignIn(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            user = userService.searchByUsername(user.getUsername());
            result.put("result", true);
            user.setPassword(null);
            result.put("user", user);
        }
        else
            result.put("result", false);
        return result;
    }
}
