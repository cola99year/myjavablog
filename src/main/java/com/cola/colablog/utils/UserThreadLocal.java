package com.cola.colablog.utils;

import com.cola.colablog.pojo.SysUser;

/**
 * @Author: cola99year
 * @Date: 2022/5/7 19:00
 */

public class UserThreadLocal {

    //私有构造器
    private UserThreadLocal(){}
    //线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}