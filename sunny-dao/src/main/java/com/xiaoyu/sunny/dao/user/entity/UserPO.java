package com.xiaoyu.sunny.dao.user.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 用户po类
 * @Author XiaoYu
 * @Date 2024/9/7 17:29
 */
@Data
public class UserPO {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
