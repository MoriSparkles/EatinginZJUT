package org.comment.controller;

import org.comment.entity.Comment;
import org.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 管理员评论管理接口
@RestController
@RequestMapping("/api/admin/comments")
public class AdminCommentController {

    @Autowired
    private CommentService commentService;

    // 管理员权限校验
    private boolean isAdmin(HttpSession session) {
        Object userId = session.getAttribute("userId");
        return userId != null && userId.equals(1);
    }

    // 获取所有评论，支持按用户/菜品筛选
    @GetMapping
    public ResponseEntity<?> getAllComments(@RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer dishId,
            HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(403).body("无权限");
        List<Comment> comments;
        if (userId != null) {
            comments = commentService.selectByUserId(userId);
        } else if (dishId != null) {
            comments = commentService.selectByDishId(dishId);
        } else {
            comments = commentService.selectAll();
        }
        return ResponseEntity.ok(comments);
    }

    // 删除评论
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id, HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(403).body("无权限");
        boolean success = commentService.deleteById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}