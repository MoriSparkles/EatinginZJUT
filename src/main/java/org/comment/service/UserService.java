package org.comment.service;

import org.comment.entity.User;

import java.util.List;

// 用户服务接口,定义用户相关的业务逻辑方法
public interface UserService {

    // 用户注册
    boolean register(User user);

    // 用户登录
    User login(String username, String password);

    // 根据用户ID查询用户
    User selectById(Integer userId);

    // 根据用户名查询用户
    User selectByName(String name);

    // 查询所有用户
    List<User> selectAll();

    // 更新用户信息
    boolean update(User user);

    // 根据用户ID删除用户
    boolean deleteById(Integer userId);

    // 检查用户名是否已存在
    boolean isUsernameExists(String name);

    // 修改用户密码
    boolean updatePassword(Integer userId, String currentPassword, String newPassword);

    // 手机号+密码登录
    User loginByPhone(String phone, String password);
}