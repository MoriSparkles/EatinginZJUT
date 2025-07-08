package org.comment.service;

import org.comment.entity.UserLikeComment;
import java.util.List;

public interface UserLikeCommentService {
    //点赞评论，参数为用户ID和评论ID，返回是否成功
    boolean like(int userId, int commentId);

    //取消点赞，参数为用户ID和评论ID，返回是否成功
    boolean unlike(int userId, int commentId);

    //查询某评论的点赞数，参数为评论ID，返回点赞数量
    int countLikes(int commentId);

    //判断用户是否已点赞某评论，参数为用户ID和评论ID，返回true/false
    boolean isLiked(int userId, int commentId);

    //查询用户所有点赞，参数为用户ID，返回点赞记录列表
    List<UserLikeComment> getLikes(int userId);
}
