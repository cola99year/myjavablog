package com.cola.colablog.service.impl;

import com.cola.colablog.mapper.SysUserMapper;
import com.cola.colablog.pojo.SysUser;
import com.cola.colablog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 18:14
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Integer authorId) {
        return  sysUserMapper.selectById(authorId);
    }
}
