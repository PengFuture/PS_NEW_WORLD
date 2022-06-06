package com.china.world.service.impl;

import com.china.world.domain.SystemUser;
import com.china.world.enums.UserEnum;
import com.china.world.excetpion.ServiceException;
import com.china.world.model.LoginUser;
import com.china.world.service.SystemMenuService;
import com.china.world.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * @author Peng
 * @date 2022/3/4 16:02
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SystemMenuService systemMenuService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = systemUserService.selectUserByUserName(username);
        if (null == systemUser) {
            logger.info("登录用户: {} 不存在!", username);
            throw new ServiceException("登录用户: " + username + " 不存在!");
        }
        if (UserEnum.DISABLE.getCode().equals(systemUser.getStatus())) {
            logger.info("登录用户: {} 已被停用!", username);
            throw new ServiceException("对不起, 您的账号: " + username + " 已停用!");
        }
        return new LoginUser(systemUser.getUserId(), systemUser, systemMenuService.getMenuPermission(systemUser));
    }
}
