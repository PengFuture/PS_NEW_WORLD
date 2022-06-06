package com.china.world.service.impl;

import com.china.world.domain.SystemUser;
import com.china.world.mapper.SystemMenuMapper;
import com.china.world.service.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Peng
 * @date 2022/3/10 14:20
 */
@Service
public class SystemMenuServiceImpl implements SystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Override
    public Set<String> getMenuPermission(SystemUser user) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            perms.addAll(systemMenuMapper.selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }
}
