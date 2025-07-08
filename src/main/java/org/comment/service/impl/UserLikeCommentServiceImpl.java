package org.comment.service.impl;

import org.comment.entity.UserLikeComment;
import org.comment.mapper.UserLikeCommentMapper;
import org.comment.service.UserLikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 标记为Spring Service组件
public class UserLikeCommentServiceImpl implements UserLikeCommentService {
    @Autowired
    private UserLikeCommentMapper mapper; // 注入Mapper，操作数据库

    @Override
    public boolean like(int userId, int commentId) {
        // 新建点赞对象并插入数据库，返回是否插入成功
        return mapper.insert(new UserLikeComment(null, userId, commentId, null)) > 0;
    }

    @Override
    public boolean unlike(int userId, int commentId) {
        // 根据用户ID和评论ID删除点赞，返回是否删除成功
        return mapper.delete(userId, commentId) > 0;
    }

    @Override
    public int countLikes(int commentId) {
        // 查询某评论的点赞数
        return mapper.countByCommentId(commentId);
    }

    @Override
    public boolean isLiked(int userId, int commentId) {
        // 判断用户是否已点赞某评论，存在返回true，否则false
        return mapper.exists(userId, commentId) > 0;
    }

    @Override
    public List<UserLikeComment> getLikes(int userId) {
        // 查询用户所有点赞，返回点赞记录列表
        return mapper.selectByUserId(userId);
    }
}
