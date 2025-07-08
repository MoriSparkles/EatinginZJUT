package org.comment.controller;

// 导入相关依赖
import org.comment.service.UserLikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.comment.mapper.CommentMapper;
import org.comment.mapper.DishMapper;
import org.comment.entity.Comment;
import org.comment.entity.Dish;
import org.comment.entity.UserLikeComment;
import java.util.*;

/**
 * 用户点赞评论相关接口控制器
 * 提供点赞、取消点赞、查询点赞状态和数量、获取用户所有点赞等RESTful接口
 * 前端通过AJAX/Fetch等方式与本控制器交互，实现点赞功能的前后端联动
 */
@RestController
@RequestMapping("/api/likes")
public class UserLikeCommentController {
    // 日志记录器，用于输出操作日志和异常信息，便于调试和排查问题
    private static final Logger logger = LoggerFactory.getLogger(UserLikeCommentController.class);

    // 注入点赞业务逻辑Service
    @Autowired
    private UserLikeCommentService likeService;
    // 注入评论Mapper，用于查询评论信息
    @Autowired
    private CommentMapper commentMapper;
    // 注入菜品Mapper，用于查询菜品信息
    @Autowired
    private DishMapper dishMapper;

    /**
     * 从session获取当前登录用户ID
     * 
     * @param session HttpSession对象，存储用户登录信息
     * @return 用户ID（Integer），未登录返回null
     */
    private Integer getCurrentUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        return userId instanceof Integer ? (Integer) userId : null;
    }

    /**
     * 点赞评论接口
     * 前端传入commentId，后端根据当前登录用户userId进行点赞操作
     * 
     * @param body    请求体，包含commentId
     * @param session 当前会话，用于获取userId
     * @return 点赞结果（success/message）
     */
    @PostMapping
    public Map<String, Object> like(@RequestBody Map<String, Integer> body, HttpSession session) {
        Integer userId = getCurrentUserId(session);
        Integer commentId = body.get("commentId");
        String sessionId = session.getId();
        logger.info("[like] userId={}, commentId={}, sessionId={}", userId, commentId, sessionId);
        try {
            boolean success = userId != null && commentId != null && likeService.like(userId, commentId);
            return Map.of("success", success, "message", success ? "点赞成功" : "点赞失败");
        } catch (Exception e) {
            logger.error("[like] Exception: ", e);
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    /**
     * 取消点赞接口
     * 前端传入commentId，后端根据当前登录用户userId取消点赞
     * 
     * @param commentId 评论ID
     * @param session   当前会话
     * @return 取消点赞结果（success/message）
     */
    @DeleteMapping("/{commentId}")
    public Map<String, Object> unlike(@PathVariable int commentId, HttpSession session) {
        Integer userId = getCurrentUserId(session);
        String sessionId = session.getId();
        logger.info("[unlike] userId={}, commentId={}, sessionId={}", userId, commentId, sessionId);
        try {
            boolean success = userId != null && likeService.unlike(userId, commentId);
            return Map.of("success", success, "message", success ? "取消点赞成功" : "取消点赞失败");
        } catch (Exception e) {
            logger.error("[unlike] Exception: ", e);
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    /**
     * 查询某评论的点赞数
     * 
     * @param commentId 评论ID
     * @return 点赞数量（count）
     */
    @GetMapping("/count")
    public Map<String, Object> count(@RequestParam int commentId) {
        int count = likeService.countLikes(commentId);
        return Map.of("count", count);
    }

    /**
     * 查询当前用户是否已点赞某评论
     * 
     * @param commentId 评论ID
     * @param session   当前会话
     * @return 是否已点赞（liked: true/false）
     */
    @GetMapping("/check")
    public Map<String, Object> check(@RequestParam int commentId, HttpSession session) {
        Integer userId = getCurrentUserId(session);
        boolean liked = userId != null && likeService.isLiked(userId, commentId);
        return Map.of("liked", liked);
    }

    /**
     * 查询当前用户所有点赞（带评论和菜品信息）
     * 用于个人中心“我的点赞”模块，前端可直接渲染详细信息
     * 
     * @param session 当前会话
     * @return 点赞列表，每项包含评论内容、菜品信息等
     */
    @GetMapping
    public List<Map<String, Object>> getLikes(HttpSession session) {
        Integer userId = getCurrentUserId(session);
        if (userId == null)
            return Collections.emptyList();
        List<UserLikeComment> likes = likeService.getLikes(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        for (UserLikeComment like : likes) {
            Comment comment = commentMapper.selectById(like.getCommentId());
            if (comment != null) {
                Dish dish = dishMapper.selectById(comment.getDishId());
                Map<String, Object> map = new HashMap<>();
                map.put("commentId", comment.getCommentId());
                map.put("commentContent", comment.getContent());
                map.put("dishId", comment.getDishId());
                map.put("dishName", dish != null ? dish.getDishName() : "");
                map.put("createdAt", comment.getCreatedAt());
                result.add(map);
            }
        }
        return result;
    }
}
