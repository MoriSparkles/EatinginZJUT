package org.comment.controller;

import org.comment.entity.UserFavoriteDish;
import org.comment.service.UserFavoriteDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import org.comment.mapper.DishMapper;
import org.comment.entity.Dish;

@RestController // 标记为REST风格控制器，返回JSON数据
@RequestMapping("/api/favorites") // 路由前缀，所有接口以/api/favorites开头
public class UserFavoriteDishController {
    private static final Logger logger = LoggerFactory.getLogger(UserFavoriteDishController.class); // 日志记录器
    @Autowired
    private UserFavoriteDishService favoriteService; // 注入收藏业务逻辑Service
    @Autowired
    private DishMapper dishMapper; // 注入菜品Mapper

    // 从session获取当前登录用户ID
    private Integer getCurrentUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        return userId instanceof Integer ? (Integer) userId : null;
    }

    // 添加收藏接口，前端传dishId，后端根据userId+dishId写入收藏表
    @PostMapping
    public Map<String, Object> addFavorite(@RequestBody Map<String, Integer> body, HttpSession session) {
        Integer userId = getCurrentUserId(session); // 获取当前用户ID
        Integer dishId = body.get("dishId"); // 获取要收藏的菜品ID
        String sessionId = session.getId(); // 获取sessionId用于日志
        logger.info("[addFavorite] userId={}, dishId={}, sessionId={}", userId, dishId, sessionId);
        try {
            boolean success = userId != null && dishId != null && favoriteService.addFavorite(userId, dishId); // 调用service添加收藏
            return Map.of("success", success, "message", success ? "收藏成功" : "收藏失败"); // 返回操作结果
        } catch (Exception e) {
            logger.error("[addFavorite] Exception: ", e);
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    // 取消收藏接口，前端传dishId，后端根据userId+dishId删除收藏
    @DeleteMapping("/{dishId}")
    public Map<String, Object> removeFavorite(@PathVariable int dishId, HttpSession session) {
        Integer userId = getCurrentUserId(session); // 获取当前用户ID
        String sessionId = session.getId(); // 获取sessionId用于日志
        logger.info("[removeFavorite] userId={}, dishId={}, sessionId={}", userId, dishId, sessionId);
        try {
            boolean success = userId != null && favoriteService.removeFavorite(userId, dishId); // 调用service取消收藏
            return Map.of("success", success, "message", success ? "取消收藏成功" : "取消收藏失败"); // 返回操作结果
        } catch (Exception e) {
            logger.error("[removeFavorite] Exception: ", e);
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    // 查询用户所有收藏（带菜品信息），用于个人中心“我的收藏”模块
    @GetMapping
    public List<Map<String, Object>> getFavorites(HttpSession session) {
        Integer userId = getCurrentUserId(session); // 获取当前用户ID
        if (userId == null)
            return Collections.emptyList(); // 未登录返回空列表
        List<UserFavoriteDish> favorites = favoriteService.getFavorites(userId); // 查询收藏记录
        List<Map<String, Object>> result = new ArrayList<>();
        for (UserFavoriteDish fav : favorites) {
            Dish dish = dishMapper.selectById(fav.getDishId()); // 查询菜品信息
            if (dish != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("dishId", dish.getDishId()); // 菜品ID
                map.put("dishName", dish.getDishName()); // 菜品名称
                map.put("dishImageUrl", dish.getImageUrl()); // 菜品图片
                map.put("campus", dish.getCampus()); // 校区
                map.put("canteen", dish.getCanteen()); // 食堂
                map.put("stall", dish.getStall()); // 档口
                map.put("price", dish.getPrice()); // 价格
                map.put("collectedAt", fav.getCreatedAt()); // 收藏时间
                result.add(map);
            }
        }
        return result; // 返回收藏菜品列表
    }

    // 查询某菜品是否被当前用户收藏，前端用于渲染收藏按钮状态
    @GetMapping("/check")
    public Map<String, Object> checkFavorite(@RequestParam int dishId, HttpSession session) {
        Integer userId = getCurrentUserId(session); // 获取当前用户ID
        boolean favorited = userId != null && favoriteService.isFavorited(userId, dishId); // 查询是否已收藏
        return Map.of("favorited", favorited); // 返回收藏状态
    }
}
