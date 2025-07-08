package org.comment.service;

import org.comment.entity.UserFavoriteDish;
import java.util.List;

public interface UserFavoriteDishService {

    //添加收藏，参数为用户ID和菜品ID，返回是否成功
    boolean addFavorite(int userId, int dishId);

    //取消收藏，参数为用户ID和菜品ID，返回是否成功
    boolean removeFavorite(int userId, int dishId);

    //查询用户所有收藏，参数为用户ID，返回收藏列表
    List<UserFavoriteDish> getFavorites(int userId);

    //判断某菜品是否被用户收藏，参数为用户ID和菜品ID，返回true/false
    boolean isFavorited(int userId, int dishId);
}
