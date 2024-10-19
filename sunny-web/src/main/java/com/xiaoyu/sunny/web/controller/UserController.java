package com.xiaoyu.sunny.web.controller;

import com.xiaoyu.sunny.service.user.dto.UserDTO;
import com.xiaoyu.sunny.service.user.service.UserService;
import com.xiaoyu.sunny.web.base.BaseResultVO;
import com.xiaoyu.sunny.web.util.ResultsUtil;
import com.xiaoyu.sunny.service.util.RocketMqHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.stream.IntStream;

/**
 * @Description: 用户web接口
 * @Author XiaoYu
 * @Date 2024/9/7 17:25
 */
@Api(tags = "用户管理", value = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RocketMqHelper rocketMqHelper;

    @ApiOperation(value="按id查询用户", notes="按id查询用户")
    @GetMapping("/{id}")
    public BaseResultVO<UserDTO> getUser(@PathVariable Long id){
        UserDTO userDTO = userService.findById(id);
        return ResultsUtil.successed(userDTO);
    }

    @ApiOperation(value="保存用户", notes="保存用户")
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

    @ApiOperation(value="按id删除用户", notes="按id删除用户")
    @GetMapping("/remove/{id}")
    public BaseResultVO<Void> removeUser(@PathVariable Long id){
        boolean flag = userService.deleteUser(id);
        if (flag) {
            return ResultsUtil.successed();
        } else {
            return ResultsUtil.failed("删除用户失败，请重试");
        }
    }

    //发送异步消息
    @ApiOperation(value="发送异步消息", notes="发送异步消息")
    @GetMapping("asyncSend")
    public BaseResultVO<Void> asyncSend() {
        IntStream.range(0, 100).forEach(
                i -> {
                    UserDTO user = new UserDTO();
                    user.setUsername("xiaoyu_"+ i);
                    user.setPhone("13520121" + i);
                    rocketMqHelper.asyncSend("USER_PRODUCER", MessageBuilder.withPayload(user).build());
                }
        );
        return ResultsUtil.successed();
    }
}
