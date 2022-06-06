package com.china.world.service;

import com.china.world.utils.AjaxResult;

/**
 * @author Peng
 * @date 2022/3/2 11:52
 */
public interface LoginService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     uuid
     * @return 生成token令牌
     */
    AjaxResult login(String username, String password, String code, String uuid);

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    AjaxResult getInfo();

}
