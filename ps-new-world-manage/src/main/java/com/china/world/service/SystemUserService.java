package com.china.world.service;

import com.china.world.domain.SystemUser;

/**
 * @author Peng
 * @date 2022/3/10 11:54
 */
public interface SystemUserService {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SystemUser selectUserByUserName(String userName);

}



