package org.comment.service;

import org.comment.entity.Comment;

import java.util.List;
import java.util.Map;

// 评论服务接口
// 定义评论相关的业务逻辑方法
public interface CommentService {

    // 添加评论
    // @param comment 评论信息
    // @return 是否添加成功
    boolean addComment(Comment comment);

    // 根据评论ID查询评论
    // @param commentId 评论ID
    // @return 评论信息
    Comment selectById(Integer commentId);

    // 查询某菜品的所有评论
    // @param dishId 菜品ID
    // @return 评论列表
    List<Comment> selectByDishId(Integer dishId);

    // 查询某用户的所有评论
    // @param userId 用户ID
    // @return 评论列表
    List<Comment> selectByUserId(Integer userId);

    // 查询某用户的所有评论（包含菜品信息）
    // @param userId 用户ID
    // @return 包含菜品信息的评论列表
    List<Map<String, Object>> selectByUserIdWithDishInfo(Integer userId);

    // 查询某菜品的所有评论（包含菜品信息）
    // @param dishId 菜品ID
    // @return 包含菜品信息的评论列表
    List<Map<String, Object>> selectByDishIdWithDishInfo(Integer dishId);

    // 查询某菜品的所有评论（包含用户信息）
    // @param dishId 菜品ID
    // @return 包含用户信息的评论列表
    List<Map<String, Object>> selectByDishIdWithUserInfo(Integer dishId);

    // 查询所有评论
    // @return 评论列表
    List<Comment> selectAll();

    // 更新评论
    // @param comment 评论信息
    // @return 是否更新成功
    boolean updateComment(Comment comment);

    // 删除评论
    // @param commentId 评论ID
    // @return 是否删除成功
    boolean deleteById(Integer commentId);

    // 获取菜品的平均评分
    // @param dishId 菜品ID
    // @return 平均评分
    Double selectAverageRatingByDishId(Integer dishId);

    // 获取菜品的评论数量
    // @param dishId 菜品ID
    // @return 评论数量
    Integer selectCommentCountByDishId(Integer dishId);

    // 获取菜品的评分统计（1-5分各有多少个）
    // @param dishId 菜品ID
    // @return 评分统计Map，key为评分，value为数量
    Map<Integer, Integer> selectRatingStatsByDishId(Integer dishId);

    // 检查用户是否已对某菜品评论过
    // @param userId 用户ID
    // @param dishId 菜品ID
    // @return 是否已评论
    boolean hasUserCommented(Integer userId, Integer dishId);
}