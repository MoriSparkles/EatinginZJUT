package org.comment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// 菜品实体类，对应数据库表 dish
@Entity
@Table(name = "dish")
@Data // Lombok注解，自动生成getter/setter等方法
@NoArgsConstructor // Lombok注解，生成无参构造器
@AllArgsConstructor // Lombok注解，生成全参构造器
public class Dish {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id", nullable = false, unique = true)
    private Integer dishId; // 菜品ID，主键，自增

    @Column(name = "dish_name", nullable = false, length = 100)
    private String dishName; // 菜品名称

    @Column(name = "campus", nullable = false, length = 100)
    private String campus; // 校区

    @Column(name = "canteen", nullable = false, length = 100)
    private String canteen; // 食堂

    @Column(name = "stall", nullable = false, length = 100)
    private String stall; // 档口

    @Column(name = "price", length = 255)
    private String price; // 价格

    @Column(name = "image_url", length = 255)
    private String imageUrl; // 图片URL

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 创建时间

    @Column(name = "description", length = 500)
    private String description; // 菜品简介
}