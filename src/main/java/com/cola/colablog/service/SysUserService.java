package com.cola.colablog.service;

import com.cola.colablog.pojo.SysUser;
import com.cola.colablog.vo.Result;
import com.cola.colablog.vo.UserVo;
import netscape.security.Principal;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 18:13
 */
public interface SysUserService {

     SysUser findUserById(Integer authorId);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Integer authorId);
}
