package com.xiaoyu.sunny.web.controller;

import com.xiaoyu.sunny.service.user.dto.UserDTO;
import com.xiaoyu.sunny.service.user.service.UserService;
import com.xiaoyu.sunny.web.base.BaseResultVO;
import com.xiaoyu.sunny.web.util.ResultsUtil;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 用户web接口
 * @Author XiaoYu
 * @Date 2024/9/7 17:25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    public BaseResultVO<UserDTO> getUser(@PathVariable Long id){
        UserDTO userDTO = userService.findById(id);
        return ResultsUtil.successed(userDTO);
    }


    @PostMapping("/save")
    public BaseResultVO<Void> saveUser(@RequestBody UserDTO userDTO){
        Assert.notNull(userDTO, "用户对象不能为空");
        boolean flag = true;
        if (userDTO.getId() == null){
            flag = userService.addUser(userDTO);
        } else {
            flag = userService.editUser(userDTO);
        }
        if (flag) {
            return ResultsUtil.successed();
        } else {
            return ResultsUtil.failed("保存用户失败，请重试");
        }
    }

    @GetMapping("/remove/{id}")
    public BaseResultVO<Void> removeUser(@PathVariable Long id){
        boolean flag = userService.deleteUser(id);
        if (flag) {
            return ResultsUtil.successed();
        } else {
            return ResultsUtil.failed("删除用户失败，请重试");
        }
    }
}
