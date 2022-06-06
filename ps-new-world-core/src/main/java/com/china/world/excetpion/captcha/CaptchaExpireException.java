package com.china.world.excetpion.captcha;

import com.china.world.excetpion.user.UserException;

/**
 * 验证码失效异常类
 *
 * @author Peng
 * @date 2022/3/4 11:19
 */
public class CaptchaExpireException extends UserException {

    public CaptchaExpireException() {
        super("user.captCha.expire", null);
    }
}
