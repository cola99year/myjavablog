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
 * @Date: 2022/6/28 23:12
 */
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private LoginService loginService;
    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);
    }
}
