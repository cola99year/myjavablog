package com.cola.colablog.controller;

import com.cola.colablog.service.LoginService;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cola99year
 * @Date: 2022/6/27 23:00
 */
@RestController
@RequestMapping("/login")
public class LoginController{

    @Autowired
    private LoginService loginService;

    @PostMapping
    //用户传来的账户和密码，封装为LoginParam类
    public Result login(@RequestBody LoginParam loginParam){
        //登录 验证用户  访问用户表
        return loginService.login(loginParam);
    }

}
