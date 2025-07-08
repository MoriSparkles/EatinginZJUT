package org.comment.service.impl;

import org.comment.entity.UserFavoriteDish;
import org.comment.mapper.UserFavoriteDishMapper;
import org.comment.service.UserFavoriteDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // 标记为Spring Service组件
public class UserFavoriteDishServiceImpl implements UserFavoriteDishService {
    @Autowired
    private UserFavoriteDishMapper mapper; // 注入Mapper，操作数据库

    @Override
    public boolean addFavorite(int userId, int dishId) {
        // 新建收藏对象并插入数据库，返回是否插入成功
        return mapper.insert(new UserFavoriteDish(null, userId, dishId, null)) > 0;
    }

    @Override
    public boolean removeFavorite(int userId, int dishId) {
        // 根据用户ID和菜品ID删除收藏，返回是否删除成功
        return mapper.delete(userId, dishId) > 0;
    }

    @Override
    public List<UserFavoriteDish> getFavorites(int userId) {
        // 查询用户所有收藏，返回收藏列表
        return mapper.selectByUserId(userId);
    }

    @Override
    public boolean isFavorited(int userId, int dishId) {
        // 判断某菜品是否被用户收藏，存在返回true，否则false
        return mapper.exists(userId, dishId) > 0;
    }
}
