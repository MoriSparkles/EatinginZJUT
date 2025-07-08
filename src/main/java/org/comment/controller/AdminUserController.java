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

// 管理员用户管理接口
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    // 管理员权限校验
    private boolean isAdmin(HttpSession session) {
        Object userId = session.getAttribute("userId");
        return userId != null && userId.equals(1);
    }

    // 获取所有用户
    @GetMapping
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(403).body("无权限");
        return ResponseEntity.ok(userService.selectAll());
    }

    // 新增用户
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user, HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(403).body("无权限");
        Map<String, Object> result = new HashMap<>();
        if (user.getPhone() != null && !user.getPhone().matches("^\\d{11}$")) {
            result.put("success", false);
            result.put("message", "手机号必须为11位数字");
            return ResponseEntity.ok(result);
        }
        if (userService.isUsernameExists(user.getName())) {
            result.put("success", false);
            result.put("message", "用户名已存在");
        } else {
            boolean success = userService.register(user);
            result.put("success", success);
            result.put("message", success ? "添加成功" : "添加失败");
        }
        return ResponseEntity.ok(result);
    }

    // 编辑用户
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user, HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(403).body("无权限");
        if (user.getPhone() != null && !user.getPhone().matches("^\\d{11}$")) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "手机号必须为11位数字");
            return ResponseEntity.ok(result);
        }
        user.setUserId(id);
        boolean success = userService.update(user);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id, HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(403).body("无权限");
        boolean success = userService.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }

    // 重置用户密码（重置为123456）
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<?> resetPassword(@PathVariable Integer id, HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(403).body("无权限");
        User user = userService.selectById(id);
        if (user == null) {
            return ResponseEntity.ok(Map.of("success", false, "message", "用户不存在"));
        }
        user.setPassword("123456");
        boolean success = userService.update(user);
        return ResponseEntity.ok(Map.of("success", success, "message", success ? "密码已重置为123456" : "重置失败"));
    }
}