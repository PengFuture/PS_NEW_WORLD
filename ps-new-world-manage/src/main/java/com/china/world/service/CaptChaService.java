package com.china.world.service;

import com.china.world.utils.AjaxResult;

/**
 * 验证码服务层
 *
 * @author Peng
 * @date 2022/3/2 10:50
 */
public interface CaptChaService {

    /**
     * 获取验证码
     *
     * @return 前端验证码
     */
    AjaxResult getVerifyCaptcha();
}
