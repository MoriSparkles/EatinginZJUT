package org.comment.controller;

import org.comment.entity.Comment;
import org.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 评论控制器，提供评论相关的 REST API
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService; // 注入评论业务逻辑Service

    // 添加评论接口，前端传Comment对象，后端写入数据库
    @PostMapping
    public ResponseEntity<Map<String, Object>> addComment(@RequestBody Comment comment) {
        Map<String, Object> result = new HashMap<>();
        boolean success = commentService.addComment(comment); // 调用service添加评论
        result.put("success", success);
        result.put("message", success ? "评论成功" : "评论失败");
        return ResponseEntity.ok(result);
    }

    // 获取某菜品的所有评论，前端用于详情页评论区
    @GetMapping("/dish/{dishId}")
    public ResponseEntity<List<Comment>> getCommentsByDish(@PathVariable Integer dishId) {
        return ResponseEntity.ok(commentService.selectByDishId(dishId));
    }

    // 获取某菜品的所有评论（包含菜品信息），用于联表展示
    @GetMapping("/dish/{dishId}/with-dish-info")
    public ResponseEntity<List<Map<String, Object>>> getCommentsByDishWithDishInfo(@PathVariable Integer dishId) {
        return ResponseEntity.ok(commentService.selectByDishIdWithDishInfo(dishId));
    }

    // 获取某菜品的所有评论（包含用户信息），用于联表展示
    @GetMapping("/dish/{dishId}/with-user-info")
    public ResponseEntity<List<Map<String, Object>>> getCommentsByDishWithUserInfo(@PathVariable Integer dishId) {
        return ResponseEntity.ok(commentService.selectByDishIdWithUserInfo(dishId));
    }

    // 获取某用户的所有评论，前端用于个人中心“我的评论”
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(commentService.selectByUserId(userId));
    }

    // 获取某用户的所有评论（包含菜品信息），用于联表展示
    @GetMapping("/user/{userId}/with-dish-info")
    public ResponseEntity<List<Map<String, Object>>> getCommentsByUserWithDishInfo(@PathVariable Integer userId) {
        return ResponseEntity.ok(commentService.selectByUserIdWithDishInfo(userId));
    }

    // 获取评论详情，前端用于评论详情页
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer commentId) {
        Comment comment = commentService.selectById(commentId); // 查询评论
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 获取菜品的平均评分和评论数，前端用于详情页评分区
    @GetMapping("/dish/{dishId}/stats")
    public ResponseEntity<Map<String, Object>> getDishStats(@PathVariable Integer dishId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("averageRating", commentService.selectAverageRatingByDishId(dishId)); // 平均评分
        stats.put("commentCount", commentService.selectCommentCountByDishId(dishId)); // 评论数
        stats.put("ratingStats", commentService.selectRatingStatsByDishId(dishId)); // 各星级分布
        return ResponseEntity.ok(stats);
    }

    // 检查用户是否已对某菜品评论，前端用于防止重复评论
    @GetMapping("/dish/{dishId}/user/{userId}/exists")
    public ResponseEntity<Map<String, Object>> hasUserCommented(@PathVariable Integer dishId,
            @PathVariable Integer userId) {
        Map<String, Object> result = new HashMap<>();
        result.put("exists", commentService.hasUserCommented(userId, dishId)); // 是否已评论
        return ResponseEntity.ok(result);
    }

    // 删除评论接口，前端传commentId，后端删除
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Integer commentId) {
        Map<String, Object> result = new HashMap<>();
        boolean success = commentService.deleteById(commentId); // 调用service删除
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }
}