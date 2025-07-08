package org.comment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// 用户实体类，对应数据库表 user
@Entity
@Table(name = "user")
@Data // Lombok注解，自动生成getter/setter等方法
@NoArgsConstructor // Lombok注解，生成无参构造器
@AllArgsConstructor // Lombok注解，生成全参构造器
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId; // 用户ID，主键，自增

    @Column(name = "name", nullable = false, length = 255)
    private String name; // 用户名

    @Column(name = "password", nullable = false, length = 255)
    private String password; // 密码（

    @Column(name = "phone", length = 20)
    private String phone; // 手机号

    // 手动添加方法，确保编译时能找到此方法
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getUserId() {
        return this.userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}