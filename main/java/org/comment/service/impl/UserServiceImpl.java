package org.comment.service.impl;

import org.comment.entity.User;
import org.comment.mapper.UserMapper;
import org.comment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 用户服务实现类
// 实现用户相关的业务逻辑
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        if (isUsernameExists(user.getName())) {
            return false;
        }
        // 执行注册
        return userMapper.insert(user) > 0;
    }

    @Override
    public User login(String username, String password) {
        return userMapper.selectByNameAndPassword(username, password);
    }

    @Override
    public User selectById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User selectByName(String name) {
        return userMapper.selectByName(name);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public boolean update(User user) {
        return userMapper.update(user) > 0;
    }

    @Override
    public boolean deleteById(Integer userId) {
        return userMapper.deleteById(userId) > 0;
    }

    @Override
    public boolean isUsernameExists(String name) {
        return userMapper.selectByName(name) != null;
    }

    @Override
    public boolean updatePassword(Integer userId, String currentPassword, String newPassword) {
        return userMapper.updatePassword(userId, currentPassword, newPassword) > 0;
    }

    @Override
    public User loginByPhone(String phone, String password) {
        return userMapper.selectByPhoneAndPassword(phone, password);
    }
}