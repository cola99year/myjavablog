package com.cola.colablog.service;

import com.cola.colablog.pojo.SysUser;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.params.LoginParam;

/**
 * @Author: cola99year
 * @Date: 2022/5/7 0:24
 */
public interface LoginService {
    //登录
    Result login(LoginParam loginParam);

    //SysUser checkToken(String token);

    //Result logout(String token);

    //Result register(LoginParam loginParam);


    /**
     * 注册
     * @param loginParam
     * @return
     */
//    Result register(LoginParam loginParam);
}
