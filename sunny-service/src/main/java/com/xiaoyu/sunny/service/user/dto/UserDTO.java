package com.xiaoyu.sunny.service.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ToString
@ApiModel
public class UserDTO {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("username")
    private String username;
    private String password;
    private String salt;
    private String email;
    private String phone;
    private Integer version;
}
