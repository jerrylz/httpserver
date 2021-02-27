package com.abchina.utils;

import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;


/**
 * 日志工具类
 * @author jerrylz
 * @date 2021/2/26
 */
public class LogUtils {

    /**
     * 自定义日志
     * @param level
     * @param clazz
     * @param msg
     * @param arguments
     */
    public static void log(Level level, Class clazz, String msg, Object... arguments){
        LogFactory.get(clazz).log(level, msg, arguments);
    }

    public static void info(Class clazz, String msg, Object... arguments){
        LogFactory.get(clazz).info(msg, arguments);
    }

}
