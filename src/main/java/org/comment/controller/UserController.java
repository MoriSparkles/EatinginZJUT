package org.comment.controller;

import org.comment.entity.User;
import org.comment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 用户控制器
// 提供用户相关的 REST API
@RestController // 标记为REST风格控制器，返回JSON数据
@RequestMapping("/api/users") // 路由前缀，所有接口以/api/users开头
public class UserController {

    @Autowired
    private UserService userService; // 注入用户业务逻辑Service

    // 用户注册接口，前端传User对象，后端校验用户名唯一性并注册
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        if (userService.isUsernameExists(user.getName())) { // 检查用户名是否已存在
            result.put("success", false);
            result.put("message", "用户名已存在");
        } else {
            boolean success = userService.register(user); // 调用service注册
            result.put("success", success);
            result.put("message", success ? "注册成功" : "注册失败");
        }
        return ResponseEntity.ok(result); // 返回注册结果
    }

    // 用户登录接口，支持用户名或手机号登录，登录成功写入session
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginForm, HttpSession session) {
        String username = loginForm.get("username"); // 获取用户名
        String password = loginForm.get("password"); // 获取密码
        String phone = loginForm.get("phone"); // 获取手机号
        Map<String, Object> result = new HashMap<>();
        User user = null;
        if (phone != null && !phone.isEmpty()) {
            user = userService.loginByPhone(phone, password); // 手机号登录
        } else if (username != null && !username.isEmpty()) {
            user = userService.login(username, password); // 用户名登录
        }
        if (user != null) {
            // 设置session，保存用户信息
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getName());
            session.setAttribute("user", user);

            result.put("success", true);
            result.put("message", "登录成功");
            result.put("user", user);
        } else {
            result.put("success", false);
            result.put("message", "用户名/手机号或密码错误");
        }
        return ResponseEntity.ok(result); // 返回登录结果
    }

    // 退出登录接口，清除session
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        session.invalidate(); // 使session失效
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "退出登录成功");
        return ResponseEntity.ok(result);
    }

    // 获取当前登录用户信息，前端可用于页面个性化展示
    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) session.getAttribute("user"); // 从session获取用户对象
        if (user != null) {
            result.put("success", true);
            result.put("user", user);
        } else {
            result.put("success", false);
            result.put("message", "用户未登录");
        }
        return ResponseEntity.ok(result);
    }

    // 查询单个用户信息，管理员或个人中心可用
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.selectById(id); // 根据ID查询用户
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 查询所有用户，管理员功能
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.selectAll());
    }

    // 更新用户信息接口，前端传User对象，后端根据ID更新
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setUserId(id); // 设置用户ID
        boolean success = userService.update(user); // 调用service更新
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    // 删除用户接口，管理员功能
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        boolean success = userService.deleteById(id); // 调用service删除
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }

    // 修改用户密码接口，前端传当前密码和新密码，后端校验后更新
    @PutMapping("/{id}/password")
    public ResponseEntity<Map<String, Object>> updatePassword(@PathVariable Integer id,
            @RequestBody Map<String, String> passwordForm) {
        String currentPassword = passwordForm.get("currentPassword"); // 当前密码
        String newPassword = passwordForm.get("newPassword"); // 新密码
        Map<String, Object> result = new HashMap<>();
        boolean success = userService.updatePassword(id, currentPassword, newPassword); // 调用service修改密码
        result.put("success", success);
        result.put("message", success ? "密码修改成功" : "当前密码错误，修改失败");
        return ResponseEntity.ok(result);
    }
}