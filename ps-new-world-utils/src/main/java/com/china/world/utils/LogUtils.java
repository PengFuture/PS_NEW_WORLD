package com.china.world.utils;

/**
 * 日志处理工具类
 *
 * @author Peng
 * @date 2022/3/3 11:45
 */
public class LogUtils {

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg + "]";
    }
}
