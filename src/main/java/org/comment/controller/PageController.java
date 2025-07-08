package org.comment.controller;

import org.comment.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

// 页面控制器
// 处理前端页面的路由跳转
@Controller
public class PageController {

    // 根路径，显示主页面
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 登录页面
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 注册页面
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // 主页（用户登录后的主页）
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        // 从session获取用户信息
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
        }
        return "home";
    }

    // 菜品列表页面
    @GetMapping("/dishes")
    public String dishes() {
        return "dishes";
    }

    // 今天吃什么页面
    @GetMapping("/recommendations")
    public String recommendations() {
        return "recommendations";
    }

    // 菜品详情页面
    @GetMapping("/dish-detail")
    public String dishDetail(@RequestParam(required = false) String id, Model model) {
        if (id == null || id.equals("undefined") || id.equals("0")) {
            return "redirect:/dishes";
        }
        try {
            Integer dishId = Integer.parseInt(id);
            model.addAttribute("dishId", dishId);
            return "dish-detail";
        } catch (NumberFormatException e) {
            return "redirect:/dishes";
        }
    }

    // 我的评论页面
    @GetMapping("/comments")
    public String myComments() {
        return "comments";
    }

    // 个人中心页面
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        // 从session获取用户信息
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "profile";
    }

    // 用户管理页面（管理员）
    @GetMapping("/admin/users")
    public String adminUsers() {
        return "admin/users";
    }

    // 菜品管理页面（管理员）
    @GetMapping("/admin/dishes")
    public String adminDishes() {
        return "admin/dishes";
    }

    // 评论管理页面（管理员）
    @GetMapping("/admin/comments")
    public String adminComments() {
        return "admin/comments";
    }

    // 统计报表页面（管理员）
    @GetMapping("/admin/stats")
    public String adminStats() {
        return "admin/stats";
    }
}