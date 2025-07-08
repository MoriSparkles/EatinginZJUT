package org.comment.mapper;

import org.apache.ibatis.annotations.*;
import org.comment.entity.User;

import java.util.List;

// 用户数据访问层接口,提供用户相关的数据库操作
@Mapper
public interface UserMapper {

    // 根据用户ID查询用户信息
    @Select("SELECT user_id, name, password, phone FROM user WHERE user_id = #{userId}")
    User selectById(@Param("userId") Integer userId);

    // 根据用户名查询用户信息
    @Select("SELECT user_id, name, password, phone FROM user WHERE name = #{name}")
    User selectByName(@Param("name") String name);

    // 根据用户名和密码查询用户信息（用于登录验证）
    @Select("SELECT user_id, name, password, phone FROM user WHERE name = #{name} AND password = #{password}")
    User selectByNameAndPassword(@Param("name") String name, @Param("password") String password);

    // 查询所有用户信息
    @Select("SELECT user_id, name, password, phone FROM user")
    List<User> selectAll();

    // 插入新用户
    @Insert("INSERT INTO user (name, password, phone) VALUES (#{name}, #{password}, #{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);

    // 更新用户信息
    int update(User user);

    // 根据用户ID删除用户
    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    int deleteById(@Param("userId") Integer userId);

    // 修改用户密码
    @Update("UPDATE user SET password = #{newPassword} WHERE user_id = #{userId} AND password = #{currentPassword}")
    int updatePassword(@Param("userId") Integer userId, @Param("currentPassword") String currentPassword,
            @Param("newPassword") String newPassword);

    // 根据手机号和密码查询用户信息（用于手机号登录）
    @Select("SELECT user_id, name, password, phone FROM user WHERE phone = #{phone} AND password = #{password}")
    User selectByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);
}
