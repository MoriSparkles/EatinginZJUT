package org.comment.mapper;

import jakarta.persistence.Table;
import org.apache.ibatis.annotations.*;
import org.comment.entity.Comment;

import java.util.List;
import java.util.Map;

// 评论数据访问层接口,提供评论相关的数据库操作
@Mapper
public interface CommentMapper {

        // 根据评论ID查询评论信息
        @Select("SELECT comment_id, user_id, dish_id, rating, content, created_at FROM comment WHERE comment_id = #{commentId}")
        Comment selectById(@Param("commentId") Integer commentId);

        // 根据菜品ID查询所有评论
        @Select("SELECT comment_id, user_id, dish_id, rating, content, created_at FROM comment WHERE dish_id = #{dishId} ORDER BY created_at DESC")
        List<Comment> selectByDishId(@Param("dishId") Integer dishId);

        // 根据用户ID查询所有评论（包含菜品信息）
        @Select("SELECT c.comment_id as commentId, c.user_id as userId, c.dish_id as dishId, c.rating, c.content, c.created_at as createdAt, "
                        +
                        "d.dish_name as dishName, d.campus, d.canteen, d.stall " +
                        "FROM comment c " +
                        "LEFT JOIN dish d ON c.dish_id = d.dish_id " +
                        "WHERE c.user_id = #{userId} " +
                        "ORDER BY c.created_at DESC")
        List<Map<String, Object>> selectByUserIdWithDishInfo(@Param("userId") Integer userId);

        // 根据菜品ID查询所有评论（包含菜品信息）
        @Select("SELECT c.comment_id as commentId, c.user_id as userId, c.dish_id as dishId, c.rating, c.content, c.created_at as createdAt, "
                        +
                        "d.dish_name as dishName, d.campus, d.canteen, d.stall " +
                        "FROM comment c " +
                        "LEFT JOIN dish d ON c.dish_id = d.dish_id " +
                        "WHERE c.dish_id = #{dishId} " +
                        "ORDER BY c.created_at DESC")
        List<Map<String, Object>> selectByDishIdWithDishInfo(@Param("dishId") Integer dishId);

        // 根据菜品ID查询所有评论（包含用户信息）
        @Select("SELECT c.comment_id as commentId, c.user_id as userId, c.dish_id as dishId, c.rating, c.content, c.created_at as createdAt, "
                        +
                        "u.name as userName " +
                        "FROM comment c " +
                        "LEFT JOIN user u ON c.user_id = u.user_id " +
                        "WHERE c.dish_id = #{dishId} " +
                        "ORDER BY c.created_at DESC")
        List<Map<String, Object>> selectByDishIdWithUserInfo(@Param("dishId") Integer dishId);

        // 根据用户ID查询所有评论
        @Select("SELECT comment_id, user_id, dish_id, rating, content, created_at FROM comment WHERE user_id = #{userId} ORDER BY created_at DESC")
        List<Comment> selectByUserId(@Param("userId") Integer userId);

        // 查询所有评论，按评论时间倒序排列
        @Select("SELECT comment_id, user_id, dish_id, rating, content, created_at FROM comment ORDER BY created_at DESC")
        List<Comment> selectAll();

        // 插入新评论
        @Insert("INSERT INTO comment (user_id, dish_id, rating, content, created_at) VALUES (#{userId}, #{dishId}, #{rating}, #{content}, NOW())")
        @Options(useGeneratedKeys = true, keyProperty = "commentId")
        int insert(Comment comment);

        // 更新评论信息
        int update(Comment comment);

        // 根据评论ID删除评论
        @Delete("DELETE FROM comment WHERE comment_id = #{commentId}")
        int deleteById(@Param("commentId") Integer commentId);

        // 根据菜品ID删除所有评论
        @Delete("DELETE FROM comment WHERE dish_id = #{dishId}")
        int deleteByDishId(@Param("dishId") Integer dishId);

        // 根据用户ID删除所有评论
        @Delete("DELETE FROM comment WHERE user_id = #{userId}")
        int deleteByUserId(@Param("userId") Integer userId);

        // 获取菜品的平均评分
        @Select("SELECT AVG(rating) FROM comment WHERE dish_id = #{dishId}")
        Double selectAverageRatingByDishId(@Param("dishId") Integer dishId);

        // 获取菜品的评论数量
        @Select("SELECT COUNT(*) FROM comment WHERE dish_id = #{dishId}")
        Integer selectCommentCountByDishId(@Param("dishId") Integer dishId);

        // 获取菜品的评分统计（1-5分各有多少个）
        @Select("SELECT rating, COUNT(*) as count FROM comment WHERE dish_id = #{dishId} GROUP BY rating ORDER BY rating")
        List<Map<String, Object>> selectRatingStatsByDishId(@Param("dishId") Integer dishId);

        // 检查用户是否已经对某个菜品评论过
        @Select("SELECT comment_id, user_id, dish_id, rating, content, created_at FROM comment WHERE user_id = #{userId} AND dish_id = #{dishId}")
        Comment selectByUserIdAndDishId(@Param("userId") Integer userId, @Param("dishId") Integer dishId);
}