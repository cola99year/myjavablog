package com.cola.colablog.service;

import com.cola.colablog.pojo.SysUser;
import netscape.security.Principal;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 18:13
 */
public interface SysUserService {

     SysUser findUserById(Integer authorId);

    SysUser findUser(String account, String password);
}
