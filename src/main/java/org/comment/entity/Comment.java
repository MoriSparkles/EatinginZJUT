package org.comment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

// 评论实体类，对应数据库表 comment
@Entity
@Table(name = "comment")
@Data // Lombok注解，自动生成getter/setter等方法
@NoArgsConstructor // Lombok注解，生成无参构造器
@AllArgsConstructor // Lombok注解，生成全参构造器
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, unique = true)
    private Integer commentId; // 评论ID，主键，自增

    @Column(name = "user_id", nullable = false)
    private Integer userId; // 用户ID，关联user表

    @Column(name = "dish_id", nullable = false)
    private Integer dishId; // 菜品ID，关联dish表

    @Column(name = "rating")
    private Integer rating; // 评分（1-5星）

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // 评论内容

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 评论时间

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user; // 关联的用户对象，便于联表查询

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", insertable = false, updatable = false)
    private Dish dish; // 关联的菜品对象，便于联表查询
}