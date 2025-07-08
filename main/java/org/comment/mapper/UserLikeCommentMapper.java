package org.comment.mapper;

import org.apache.ibatis.annotations.*;
import org.comment.entity.UserLikeComment;
import java.util.List;

@Mapper // 标记为MyBatis Mapper接口
public interface UserLikeCommentMapper {
    @Insert("INSERT INTO user_like_comment(user_id, comment_id) VALUES(#{userId}, #{commentId})")
    int insert(UserLikeComment like); // 插入点赞记录，参数为UserLikeComment对象

    @Delete("DELETE FROM user_like_comment WHERE user_id = #{userId} AND comment_id = #{commentId}")
    int delete(@Param("userId") int userId, @Param("commentId") int commentId); // 删除点赞记录，参数为用户ID和评论ID

    @Select("SELECT COUNT(*) FROM user_like_comment WHERE comment_id = #{commentId}")
    int countByCommentId(@Param("commentId") int commentId); // 查询某评论的点赞数

    @Select("SELECT COUNT(*) FROM user_like_comment WHERE user_id = #{userId} AND comment_id = #{commentId}")
    int exists(@Param("userId") int userId, @Param("commentId") int commentId); // 判断用户是否已点赞某评论

    @Select("SELECT * FROM user_like_comment WHERE user_id = #{userId}")
    List<UserLikeComment> selectByUserId(@Param("userId") int userId); // 查询用户所有点赞记录
}
