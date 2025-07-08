package org.comment.service.impl;

import org.comment.entity.Dish;
import org.comment.mapper.DishMapper;
import org.comment.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 菜品服务实现类
// 实现菜品相关的业务逻辑
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public Dish selectById(Integer dishId) {
        return dishMapper.selectById(dishId);
    }

    @Override
    public List<Dish> selectAll() {
        return dishMapper.selectAll();
    }

    @Override
    public List<Dish> selectByCampus(String campus) {
        return dishMapper.selectByCampus(campus);
    }

    @Override
    public List<Dish> selectByCanteen(String canteen) {
        return dishMapper.selectByCanteen(canteen);
    }

    @Override
    public List<Dish> selectByStall(String stall) {
        return dishMapper.selectByStall(stall);
    }

    @Override
    public List<Dish> selectByCampusAndCanteen(String campus, String canteen) {
        return dishMapper.selectByCampusAndCanteen(campus, canteen);
    }

    @Override
    public List<Dish> selectByCampusAndCanteenAndStall(String campus, String canteen, String stall) {
        return dishMapper.selectByCampusAndCanteenAndStall(campus, canteen, stall);
    }

    @Override
    public List<Dish> searchByDishName(String dishName) {
        return dishMapper.searchByDishName(dishName);
    }

    @Override
    public boolean addDish(Dish dish) {
        // 设置创建时间
        dish.setCreatedAt(LocalDateTime.now());
        return dishMapper.insert(dish) > 0;
    }

    @Override
    public boolean updateDish(Dish dish) {
        return dishMapper.update(dish) > 0;
    }

    @Override
    public boolean deleteDish(Integer dishId) {
        return dishMapper.deleteById(dishId) > 0;
    }

    @Override
    public List<String> selectAllCampuses() {
        return dishMapper.selectAllCampuses();
    }

    @Override
    public List<String> selectCanteensByCampus(String campus) {
        return dishMapper.selectCanteensByCampus(campus);
    }

    @Override
    public List<String> selectStallsByCampusAndCanteen(String campus, String canteen) {
        return dishMapper.selectStallsByCampusAndCanteen(campus, canteen);
    }

    @Override
    public Dish getRandomDish() {
        return dishMapper.selectRandomDish();
    }

    @Override
    public List<Dish> getRandomDishes(Integer count) {
        return dishMapper.selectRandomDishes(count);
    }

    @Override
    public List<Dish> aiRecommendDishes(String userInput) {
        List<Dish> allDishes = dishMapper.selectAll();
        if (allDishes == null || allDishes.isEmpty())
            return List.of();
        String input = userInput == null ? "" : userInput.trim();
        // 简单关键词匹配
        List<Dish> matched = allDishes.stream().filter(dish -> {
            String text = (dish.getDishName() + " " + dish.getDescription()).toLowerCase();
            return input.isEmpty() || text.contains(input.toLowerCase())
                    || (input.contains("辣") && text.contains("辣"))
                    || (input.contains("素") && (text.contains("素") || text.contains("蔬菜") || text.contains("青菜")))
                    || (input.contains("川菜") && text.contains("川"));
        }).toList();
        // 若匹配不足3个，补足随机菜品
        List<Dish> result = new java.util.ArrayList<>();
        java.util.Collections.shuffle(matched);
        result.addAll(matched.stream().limit(3).toList());
        if (result.size() < 3) {
            java.util.Collections.shuffle(allDishes);
            for (Dish d : allDishes) {
                if (result.size() >= 3)
                    break;
                if (!result.contains(d))
                    result.add(d);
            }
        }
        return result;
    }
}