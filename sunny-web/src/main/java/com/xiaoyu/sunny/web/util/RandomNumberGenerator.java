package com.xiaoyu.sunny.web.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/21 19:51
 */
public class RandomNumberGenerator {
    public static int getRandomNumber() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(1, 201);
    }
}
