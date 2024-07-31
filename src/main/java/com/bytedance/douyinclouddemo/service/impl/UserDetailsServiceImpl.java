package com.bytedance.douyinclouddemo.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.bytedance.douyinclouddemo.mapper.AnchorUserMapper;
import com.bytedance.douyinclouddemo.pojo.AnchorUser;
import com.bytedance.douyinclouddemo.pojo.CodeConstant;
import com.bytedance.douyinclouddemo.pojo.OperaResult;
import com.bytedance.douyinclouddemo.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, org.springframework.security.core.userdetails.UserDetailsService {

    private final AnchorUserMapper anchorUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(@Lazy AnchorUserMapper anchorUserMapper, @Lazy PasswordEncoder passwordEncoder) {
        this.anchorUserMapper = anchorUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<AnchorUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", username);
        AnchorUser anchorUser = anchorUserMapper.selectOne(queryWrapper);

        if (anchorUser == null) {
            throw new UsernameNotFoundException("User not found with account: " + username);
        }
        return new org.springframework.security.core.userdetails.User(anchorUser.getAccount(), anchorUser.getPassword(), new ArrayList<>());
    }

    @Override
    public OperaResult registerUser(String account, String password) {
        AnchorUser anchorUser1 = anchorUserMapper.selectOne(new LambdaQueryWrapper<AnchorUser>().eq(AnchorUser::getAccount, account));
        if (anchorUser1 != null) {
            return new OperaResult(CodeConstant.SUCCESS.getCode(), CodeConstant.CREATION_FAILED.getMessage());
        }
        AnchorUser anchorUser = new AnchorUser();
        anchorUser.setId(IdUtil.randomUUID());
        anchorUser.setAccount(account);
        //创建默认成为散客
        anchorUser.setRoleId("665985585");
        anchorUser.setPassword(passwordEncoder.encode(password));
        anchorUserMapper.insert(anchorUser);
        return new OperaResult(CodeConstant.SUCCESS.getCode(), CodeConstant.SUCCESS.getMessage());
    }

    @Override
    public AnchorUser findByAccount(String account) {
        QueryWrapper<AnchorUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        return anchorUserMapper.selectOne(queryWrapper);
    }
}
