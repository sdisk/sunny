package com.xiaoyu.sunny.service.user.service.impl;

import com.xiaoyu.sunny.dao.user.dao.UserDao;
import com.xiaoyu.sunny.service.user.dto.UserDTO;
import com.xiaoyu.sunny.service.user.service.UserService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @Description: 用户服务实现
 * @Author XiaoYu
 * @Date 2024/9/7 17:33
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDTO findById(Integer id) {
        //先静态模拟数据
        //UserPO userPO = userDao.getById(id);
        return UserDTO.builder()
                .id(1L)
                .username("aaa")
                .phone("123456789")
                .build();
    }
}
