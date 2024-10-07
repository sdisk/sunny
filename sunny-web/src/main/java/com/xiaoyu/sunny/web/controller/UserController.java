package com.xiaoyu.sunny.web.controller;

import com.xiaoyu.sunny.service.user.dto.UserDTO;
import com.xiaoyu.sunny.service.user.service.UserService;
import com.xiaoyu.sunny.web.base.BaseResultVO;
import com.xiaoyu.sunny.web.util.ResultsUtil;
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
    public BaseResultVO<UserDTO> getUser(@PathVariable Integer id){
        //查询商品
        UserDTO userDTO = userService.findById(id);
        return ResultsUtil.successed(userDTO);
    }
}
