package org.comment.service.impl;

import org.comment.entity.Comment;
import org.comment.mapper.CommentMapper;
import org.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 评论服务实现类
// 实现评论相关的业务逻辑
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean addComment(Comment comment) {
        return commentMapper.insert(comment) > 0;
    }

    @Override
    public Comment selectById(Integer commentId) {
        return commentMapper.selectById(commentId);
    }

    @Override
    public List<Comment> selectByDishId(Integer dishId) {
        return commentMapper.selectByDishId(dishId);
    }

    @Override
    public List<Comment> selectByUserId(Integer userId) {
        return commentMapper.selectByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> selectByUserIdWithDishInfo(Integer userId) {
        return commentMapper.selectByUserIdWithDishInfo(userId);
    }

    @Override
    public List<Map<String, Object>> selectByDishIdWithDishInfo(Integer dishId) {
        return commentMapper.selectByDishIdWithDishInfo(dishId);
    }

    @Override
    public List<Map<String, Object>> selectByDishIdWithUserInfo(Integer dishId) {
        return commentMapper.selectByDishIdWithUserInfo(dishId);
    }

    @Override
    public List<Comment> selectAll() {
        return commentMapper.selectAll();
    }

    @Override
    public boolean updateComment(Comment comment) {
        return commentMapper.update(comment) > 0;
    }

    @Override
    public boolean deleteById(Integer commentId) {
        return commentMapper.deleteById(commentId) > 0;
    }

    @Override
    public Double selectAverageRatingByDishId(Integer dishId) {
        return commentMapper.selectAverageRatingByDishId(dishId);
    }

    @Override
    public Integer selectCommentCountByDishId(Integer dishId) {
        return commentMapper.selectCommentCountByDishId(dishId);
    }

    @Override
    public Map<Integer, Integer> selectRatingStatsByDishId(Integer dishId) {
        List<Map<String, Object>> stats = commentMapper.selectRatingStatsByDishId(dishId);
        Map<Integer, Integer> result = new HashMap<>();
        if (stats != null) {
            for (Map<String, Object> stat : stats) {
                Object ratingObj = stat.get("rating");
                Object countObj = stat.get("count");
                if (ratingObj != null && countObj != null) {
                    result.put(Integer.parseInt(ratingObj.toString()), Integer.parseInt(countObj.toString()));
                }
            }
        }
        return result;
    }

    @Override
    public boolean hasUserCommented(Integer userId, Integer dishId) {
        return commentMapper.selectByUserIdAndDishId(userId, dishId) != null;
    }
}