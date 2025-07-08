package org.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Lombok注解，自动生成getter/setter等方法
@AllArgsConstructor // Lombok注解，生成全参构造器
@NoArgsConstructor // Lombok注解，生成无参构造器
public class UserFavoriteDish {
    private Integer id; // 主键，自增ID
    private Integer userId; // 用户ID，关联user表
    private Integer dishId; // 菜品ID，关联dish表
    private LocalDateTime createdAt; // 收藏时间，记录添加时间
}
