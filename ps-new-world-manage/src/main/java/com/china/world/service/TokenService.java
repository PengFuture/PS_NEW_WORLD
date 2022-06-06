package com.china.world.service;

import com.china.world.model.LoginUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Peng
 * @date 2022/3/4 14:32
 */
public interface TokenService {

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    String createToken(LoginUser loginUser);

    /**
     * 获取用户信息
     *
     * @param request 用户身份信息
     * @return 用户信息
     */
    LoginUser getLoginUser(HttpServletRequest request);

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser 用户信息
     */
    void verifyToken(LoginUser loginUser);

    /**
     * 删除缓存中用户登录信息
     *
     * @param token 用户唯一标识
     */
    void deleteRedisLoginUser(String token);
}
