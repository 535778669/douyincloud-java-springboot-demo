package com.bytedance.douyinclouddemo.service;


import com.bytedance.douyinclouddemo.pojo.AnchorUser;
import com.bytedance.douyinclouddemo.pojo.OperaResult;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserDetailsService {
    UserDetails loadUserByUsername(String username);

    OperaResult registerUser(String account, String password);

    AnchorUser findByAccount(String account);

}
