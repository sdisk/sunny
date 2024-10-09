package com.xiaoyu.sunny.dao.user.dao;


import com.xiaoyu.sunny.dao.user.entity.UserPO;

/**
 * @Description: 用户DAO
 * @Author XiaoYu
 * @Date 2024/9/7 17:38
 */
public interface UserDao {

    UserPO getById(Long id);

    boolean addUser(UserPO userPO);

    boolean updateUser(UserPO userPO);

    boolean deleteUser(Long id);

}
