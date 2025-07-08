package org.comment.controller;

import org.comment.entity.Dish;
import org.comment.service.DishService;
import org.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 菜品控制器
// 提供菜品相关的 REST API
@RestController // 标记为REST风格控制器，返回JSON数据
@RequestMapping("/api/dishes") // 路由前缀，所有接口以/api/dishes开头
public class DishController {

    @Autowired
    private DishService dishService; // 注入菜品业务逻辑Service
    @Autowired
    private CommentService commentService; // 注入评论业务逻辑Service

    // 获取所有菜品（支持多条件筛选和分页），前端用于菜品列表页
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDishes(
            @RequestParam(required = false) String campus, // 校区
            @RequestParam(required = false) String canteen, // 食堂
            @RequestParam(required = false) String stall, // 档口
            @RequestParam(required = false) String search, // 关键词搜索
            @RequestParam(defaultValue = "1") int page, // 页码
            @RequestParam(defaultValue = "12") int size) { // 每页数量

        List<Dish> dishes;
        if (search != null && !search.isEmpty()) {
            dishes = dishService.searchByDishName(search); // 按名称搜索
        } else if (campus != null && canteen != null && stall != null && !stall.isEmpty()) {
            dishes = dishService.selectByCampusAndCanteenAndStall(campus, canteen, stall); // 多条件筛选
        } else if (campus != null && canteen != null) {
            dishes = dishService.selectByCampusAndCanteen(campus, canteen);
        } else if (campus != null) {
            dishes = dishService.selectByCampus(campus);
        } else if (canteen != null) {
            dishes = dishService.selectByCanteen(canteen);
        } else if (stall != null) {
            dishes = dishService.selectByStall(stall);
        } else {
            dishes = dishService.selectAll(); // 查询全部
        }

        // 手动分页
        int totalSize = dishes.size();
        int totalPages = (int) Math.ceil((double) totalSize / size);
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalSize);

        List<Dish> pagedDishes = dishes.subList(startIndex, endIndex);

        // 组装带评分和评论数的返回内容
        List<Map<String, Object>> content = new java.util.ArrayList<>();
        for (Dish dish : pagedDishes) {
            Map<String, Object> map = new HashMap<>();
            map.put("dishId", dish.getDishId()); // 菜品ID
            map.put("campus", dish.getCampus()); // 校区
            map.put("canteen", dish.getCanteen()); // 食堂
            map.put("stall", dish.getStall()); // 档口
            map.put("dishName", dish.getDishName()); // 菜品名称
            map.put("price", dish.getPrice()); // 价格
            map.put("description", dish.getDescription()); // 简介
            map.put("createdAt", dish.getCreatedAt()); // 创建时间
            map.put("imageUrl", dish.getImageUrl()); // 图片
            map.put("averageRating", commentService.selectAverageRatingByDishId(dish.getDishId())); // 平均评分
            map.put("commentCount", commentService.selectCommentCountByDishId(dish.getDishId())); // 评论数
            content.add(map);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("content", content); // 当前页内容
        result.put("totalPages", totalPages); // 总页数
        result.put("currentPage", page); // 当前页码
        result.put("totalSize", totalSize); // 总条数
        result.put("size", size); // 每页数量

        return ResponseEntity.ok(result); // 返回分页结果
    }

    // 获取单个菜品详情（含平均评分和评论数），前端用于详情页
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDishDetail(@PathVariable Integer id) {
        Dish dish = dishService.selectById(id); // 查询菜品
        if (dish == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("dish", dish); // 菜品信息
        result.put("averageRating", commentService.selectAverageRatingByDishId(id)); // 平均评分
        result.put("commentCount", commentService.selectCommentCountByDishId(id)); // 评论数
        result.put("ratingStats", commentService.selectRatingStatsByDishId(id)); // 各星级分布
        return ResponseEntity.ok(result);
    }

    // 新增菜品接口，管理员功能
    @PostMapping
    public ResponseEntity<Map<String, Object>> addDish(@RequestBody Dish dish) {
        boolean success = dishService.addDish(dish); // 调用service新增
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "添加成功" : "添加失败");
        return ResponseEntity.ok(result);
    }

    // 更新菜品接口，管理员功能
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateDish(@PathVariable Integer id, @RequestBody Dish dish) {
        dish.setDishId(id); // 设置ID
        boolean success = dishService.updateDish(dish); // 调用service更新
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "更新成功" : "更新失败");
        return ResponseEntity.ok(result);
    }

    // 删除菜品接口，管理员功能
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteDish(@PathVariable Integer id) {
        boolean success = dishService.deleteDish(id); // 调用service删除
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", success ? "删除成功" : "删除失败");
        return ResponseEntity.ok(result);
    }

    // 获取所有校区，前端用于筛选
    @GetMapping("/campuses")
    public ResponseEntity<List<String>> getAllCampuses() {
        return ResponseEntity.ok(dishService.selectAllCampuses());
    }

    // 获取指定校区的所有食堂，前端用于筛选
    @GetMapping("/canteens")
    public ResponseEntity<List<String>> getCanteensByCampus(@RequestParam String campus) {
        return ResponseEntity.ok(dishService.selectCanteensByCampus(campus));
    }

    // 获取指定校区和食堂的所有档口，前端用于筛选
    @GetMapping("/stalls")
    public ResponseEntity<List<String>> getStallsByCampusAndCanteen(@RequestParam String campus,
            @RequestParam String canteen) {
        return ResponseEntity.ok(dishService.selectStallsByCampusAndCanteen(campus, canteen));
    }

    // 随机推荐菜品，前端用于“随机推荐”功能
    @GetMapping("/random")
    public ResponseEntity<Map<String, Object>> getRandomDish(@RequestParam(required = false) Integer count) {
        try {
            if (count != null && count > 1) {
                // 返回多个随机菜品
                List<Dish> randomDishes = dishService.getRandomDishes(count);
                List<Map<String, Object>> dishesWithRating = new java.util.ArrayList<>();
                for (Dish dish : randomDishes) {
                    Map<String, Object> dishMap = new HashMap<>();
                    dishMap.put("dishId", dish.getDishId()); // 菜品ID
                    dishMap.put("campus", dish.getCampus()); // 校区
                    dishMap.put("canteen", dish.getCanteen()); // 食堂
                    dishMap.put("stall", dish.getStall()); // 档口
                    dishMap.put("dishName", dish.getDishName()); // 菜品名称
                    dishMap.put("price", dish.getPrice()); // 价格
                    dishMap.put("description", dish.getDescription()); // 简介
                    dishMap.put("averageRating", commentService.selectAverageRatingByDishId(dish.getDishId())); // 平均评分
                    dishMap.put("commentCount", commentService.selectCommentCountByDishId(dish.getDishId())); // 评论数
                    dishesWithRating.add(dishMap);
                }

                Map<String, Object> result = new HashMap<>();
                result.put("success", true);
                result.put("dishes", dishesWithRating); // 返回多个菜品
                return ResponseEntity.ok(result);
            } else {
                // 返回单个随机菜品
                Dish randomDish = dishService.getRandomDish();
                if (randomDish == null) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("success", false);
                    result.put("message", "没有找到菜品");
                    return ResponseEntity.ok(result);
                }

                Map<String, Object> dishMap = new HashMap<>();
                dishMap.put("dishId", randomDish.getDishId()); // 菜品ID
                dishMap.put("campus", randomDish.getCampus()); // 校区
                dishMap.put("canteen", randomDish.getCanteen()); // 食堂
                dishMap.put("stall", randomDish.getStall()); // 档口
                dishMap.put("dishName", randomDish.getDishName()); // 菜品名称
                dishMap.put("price", randomDish.getPrice()); // 价格
                dishMap.put("description", randomDish.getDescription()); // 简介
                dishMap.put("imageUrl", randomDish.getImageUrl()); // 图片
                dishMap.put("averageRating", commentService.selectAverageRatingByDishId(randomDish.getDishId())); // 平均评分
                dishMap.put("commentCount", commentService.selectCommentCountByDishId(randomDish.getDishId())); // 评论数

                Map<String, Object> result = new HashMap<>();
                result.put("success", true);
                result.put("dish", dishMap); // 返回单个菜品
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "获取随机菜品失败: " + e.getMessage());
            return ResponseEntity.ok(result);
        }
    }

    // AI智能推荐菜品，前端传入用户需求，后端返回推荐结果
    @PostMapping("/ai-recommend")
    public ResponseEntity<List<Dish>> aiRecommend(@RequestBody Map<String, String> body) {
        String userInput = body.getOrDefault("userInput", ""); // 用户输入
        List<Dish> dishes = dishService.aiRecommendDishes(userInput); // 调用AI推荐
        return ResponseEntity.ok(dishes);
    }
}