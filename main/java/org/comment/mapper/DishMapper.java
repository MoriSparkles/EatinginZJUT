package org.comment.mapper;

import org.apache.ibatis.annotations.*;
import org.comment.entity.Dish;

import java.util.List;

// 菜品数据访问层接口,提供菜品相关的数据库操作
@Mapper
public interface DishMapper {

    // 根据菜品ID查询菜品信息
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish WHERE dish_id = #{dishId}")
    Dish selectById(@Param("dishId") Integer dishId);

    // 查询所有菜品，按创建时间倒序排列
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish ORDER BY created_at DESC")
    List<Dish> selectAll();

    // 根据校区筛选菜品
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish WHERE campus = #{campus} ORDER BY created_at DESC")
    List<Dish> selectByCampus(@Param("campus") String campus);

    // 根据食堂筛选菜品
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish WHERE canteen = #{canteen} ORDER BY created_at DESC")
    List<Dish> selectByCanteen(@Param("canteen") String canteen);

    // 根据档口筛选菜品
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish WHERE stall = #{stall} ORDER BY created_at DESC")
    List<Dish> selectByStall(@Param("stall") String stall);

    // 根据校区和食堂筛选菜品
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish WHERE campus = #{campus} AND canteen = #{canteen} ORDER BY created_at DESC")
    List<Dish> selectByCampusAndCanteen(@Param("campus") String campus, @Param("canteen") String canteen);

    // 根据校区、食堂和档口筛选菜品
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish WHERE campus = #{campus} AND canteen = #{canteen} AND stall = #{stall} ORDER BY created_at DESC")
    List<Dish> selectByCampusAndCanteenAndStall(@Param("campus") String campus, @Param("canteen") String canteen,
            @Param("stall") String stall);

    // 根据菜品名称模糊搜索
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish WHERE dish_name LIKE CONCAT('%', #{dishName}, '%') ORDER BY created_at DESC")
    List<Dish> searchByDishName(@Param("dishName") String dishName);

    // 插入新菜品
    @Insert("INSERT INTO dish (campus, canteen, stall, dish_name, price, image_url, description, created_at) VALUES (#{campus}, #{canteen}, #{stall}, #{dishName}, #{price}, #{imageUrl}, #{description}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "dishId")
    int insert(Dish dish);

    // 更新菜品信息
    int update(Dish dish);

    // 根据菜品ID删除菜品
    @Delete("DELETE FROM dish WHERE dish_id = #{dishId}")
    int deleteById(@Param("dishId") Integer dishId);

    // 获取所有校区
    @Select("SELECT DISTINCT campus FROM dish ORDER BY campus")
    List<String> selectAllCampuses();

    // 获取指定校区的所有食堂
    @Select("SELECT DISTINCT canteen FROM dish WHERE campus = #{campus} ORDER BY canteen")
    List<String> selectCanteensByCampus(@Param("campus") String campus);

    // 获取指定校区和食堂的所有档口
    @Select("SELECT DISTINCT stall FROM dish WHERE campus = #{campus} AND canteen = #{canteen} ORDER BY stall")
    List<String> selectStallsByCampusAndCanteen(@Param("campus") String campus, @Param("canteen") String canteen);

    // 随机获取一个菜品
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish ORDER BY RAND() LIMIT 1")
    Dish selectRandomDish();

    // 随机获取多个菜品
    @Select("SELECT dish_id, campus, canteen, stall, dish_name, price, image_url, description, created_at FROM dish ORDER BY RAND() LIMIT #{count}")
    List<Dish> selectRandomDishes(@Param("count") Integer count);
}