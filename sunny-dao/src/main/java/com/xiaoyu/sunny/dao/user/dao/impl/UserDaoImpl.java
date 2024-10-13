package com.xiaoyu.sunny.dao.user.dao.impl;

import com.xiaoyu.sunny.dao.user.dao.UserDao;
import com.xiaoyu.sunny.dao.user.entity.UserPO;
import com.xiaoyu.sunny.dao.user.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Description: 用户DAO实现
 * @Author XiaoYu
 * @Date 2024/9/7 17:53
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserPO getById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean addUser(UserPO userPO) {
        int number = userMapper.insert(userPO);
        return number == 1;
    }

    @Override
    public int updateUser(UserPO userPO) {
        return userMapper.updateByPrimaryKeySelective(userPO);
    }

    @Override
    public boolean deleteUser(Long id) {
        int number = userMapper.deleteByPrimaryKey(id);
        return number == 1;
    }
}
