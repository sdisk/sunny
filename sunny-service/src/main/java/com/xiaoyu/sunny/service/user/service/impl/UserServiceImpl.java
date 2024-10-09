package com.xiaoyu.sunny.service.user.service.impl;

import com.xiaoyu.sunny.dao.user.dao.UserDao;
import com.xiaoyu.sunny.dao.user.entity.UserPO;
import com.xiaoyu.sunny.service.user.dto.UserDTO;
import com.xiaoyu.sunny.service.user.service.UserService;
import com.xiaoyu.sunny.service.util.Md5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
    public UserDTO findById(Long id) {
        log.info("[UserServiceImpl.findById] id:{}", id);
        Assert.notNull(id, "用户ID不能为空");
        UserPO userPO = userDao.getById(id);
        return UserDTO.builder()
                .id(userPO.getId())
                .username(userPO.getUsername())
                .phone(userPO.getPhone())
                .build();
    }

    @Override
    public boolean addUser(UserDTO userDTO) {
        log.info("[UserServiceImpl.addUser] userDTO:{}", userDTO);
        Assert.notNull(userDTO, "用户对象不能为空");
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userDTO, userPO);
        String salt = Md5Util.getSalt();
        userPO.setSalt(salt);
        userPO.setPassword(Md5Util.getMd5Encryption(userDTO.getPassword(), salt));
        LocalDateTime now = LocalDateTime.now();
        userPO.setCreateTime(now);
        userPO.setUpdateTime(now);
        return userDao.addUser(userPO);
    }

    @Override
    public boolean editUser(UserDTO userDTO) {
        log.info("[UserServiceImpl.editUser] userDTO:{}", userDTO);
        Assert.notNull(userDTO, "用户对象不能为空");
        UserPO userPO = userDao.getById(userDTO.getId());
        userPO.setUsername(userDTO.getUsername());
        userPO.setPhone(userDTO.getPhone());
        return userDao.updateUser(userPO);
    }

    @Override
    public boolean deleteUser(Long id) {
        log.info("[UserServiceImpl.deleteUser] userId:{}", id);
        Assert.notNull(id, "用户ID不能为空");
        return userDao.deleteUser(id);
    }
}
