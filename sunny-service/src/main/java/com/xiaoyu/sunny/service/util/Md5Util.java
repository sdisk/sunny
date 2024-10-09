package com.xiaoyu.sunny.service.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @Description: 动态盐的MD5加密
 * @Author XiaoYu
 * @Date 2024/9/9 14:21
 */
public class Md5Util {

    public static String getMd5Encryption(String password, String salt){
        return DigestUtils.md5DigestAsHex((password+salt).getBytes(StandardCharsets.UTF_8));
    }

    public static String getSalt() {
        String str = "zxcvbnmasdfghjklqwertyuiopzxcvbiwasdfghjklqwertyui0p1234567890,.<):?";

        Random random = new Random();
        StringBuffer stringbuffer = new StringBuffer();
        //循环16次，共取出16个随机字符
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(68);
            stringbuffer.append(str.charAt(number));
        }
        return stringbuffer.toString();
    }

}
