package com.china.world.utils;


/**
 * @author Peng
 * @date 2021/11/3 9:18
 */
public class IdUtils {

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUuid() {
        return UUID.randomUuid().toString(true);
    }


    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUuid() {
        return UUID.fastUuid().toString();
    }

}
