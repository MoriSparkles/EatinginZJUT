package org.comment.service;

import org.comment.entity.Dish;

import java.util.List;

// 菜品服务接口,定义菜品相关的业务逻辑方法
public interface DishService {

    // 根据菜品ID查询菜品
    Dish selectById(Integer dishId);

    // 查询所有菜品
    List<Dish> selectAll();

    // 根据校区筛选菜品
    List<Dish> selectByCampus(String campus);

    // 根据食堂筛选菜品
    List<Dish> selectByCanteen(String canteen);

    // 根据档口筛选菜品
    List<Dish> selectByStall(String stall);

    // 根据校区和食堂筛选菜品
    List<Dish> selectByCampusAndCanteen(String campus, String canteen);

    // 根据校区、食堂和档口筛选菜品
    List<Dish> selectByCampusAndCanteenAndStall(String campus, String canteen, String stall);

    // 根据菜品名称搜索
    List<Dish> searchByDishName(String dishName);

    // 添加新菜品
    boolean addDish(Dish dish);

    // 更新菜品信息
    boolean updateDish(Dish dish);

    // 删除菜品
    boolean deleteDish(Integer dishId);

    // 获取所有校区
    List<String> selectAllCampuses();

    // 获取指定校区的所有食堂
    List<String> selectCanteensByCampus(String campus);

    // 获取指定校区和食堂的所有档口
    List<String> selectStallsByCampusAndCanteen(String campus, String canteen);

    // 随机获取一个菜品
    Dish getRandomDish();

    // 随机获取多个菜品
    List<Dish> getRandomDishes(Integer count);

    // AI智能推荐菜品
    List<Dish> aiRecommendDishes(String userInput);
}