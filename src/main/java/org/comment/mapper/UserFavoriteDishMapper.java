package org.comment.mapper;

import org.apache.ibatis.annotations.*;
import org.comment.entity.UserFavoriteDish;
import java.util.List;

@Mapper // 标记为MyBatis Mapper接口
public interface UserFavoriteDishMapper {
    @Insert("INSERT INTO user_favorite_dish(user_id, dish_id) VALUES(#{userId}, #{dishId})")
    int insert(UserFavoriteDish favorite); // 插入收藏记录，参数为UserFavoriteDish对象

    @Delete("DELETE FROM user_favorite_dish WHERE user_id = #{userId} AND dish_id = #{dishId}")
    int delete(@Param("userId") int userId, @Param("dishId") int dishId); // 删除收藏记录，参数为用户ID和菜品ID

    @Select("SELECT * FROM user_favorite_dish WHERE user_id = #{userId}")
    List<UserFavoriteDish> selectByUserId(@Param("userId") int userId); // 查询用户所有收藏，参数为用户ID

    @Select("SELECT COUNT(*) FROM user_favorite_dish WHERE user_id = #{userId} AND dish_id = #{dishId}")
    int exists(@Param("userId") int userId, @Param("dishId") int dishId); // 判断某菜品是否被用户收藏
}
