package com.xiaoyu.sunny.service.user.service;

import com.xiaoyu.sunny.service.user.dto.UserDTO;

/**
 * @Description: 用户service接口
 * @Author XiaoYu
 * @Date 2024/9/7 17:32
 */
public interface UserService {

    UserDTO findById(Long id);

    boolean addUser(UserDTO userDTO);

    boolean editUser(UserDTO userDTO);

    boolean deleteUser(Long id);
}
