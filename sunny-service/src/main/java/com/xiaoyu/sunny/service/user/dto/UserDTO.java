package com.xiaoyu.sunny.service.user.dto;

import lombok.*;

/**
 * @Description: 用户DTO对象
 * @Author XiaoYu
 * @Date 2024/9/7 17:31
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private String phone;
}
