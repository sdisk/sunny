package com.xiaoyu.sunny.dao.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Description: 用户po类
 * @Author XiaoYu
 * @Date 2024/9/7 17:29
 */
@Table(name = "T_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPO {
    @Id
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
