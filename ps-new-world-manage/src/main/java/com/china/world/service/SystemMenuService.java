package com.china.world.service;

import com.china.world.domain.SystemUser;

import java.util.Set;

/**
 * @author Peng
 * @date 2022/3/10 14:20
 */
public interface SystemMenuService {

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    Set<String> getMenuPermission(SystemUser user);
}
