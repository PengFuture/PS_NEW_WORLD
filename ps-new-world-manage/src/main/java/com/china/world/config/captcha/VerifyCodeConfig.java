package com.china.world.config.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * yml文件 验证码配置类
 *
 * @author Peng
 * @date 2022/3/2 11:01
 */
@Configuration
public class VerifyCodeConfig {

    @Value("${security.captcha.method}")
    private String captchaType;

    @Value("${security.captcha.captchaOnOff}")
    private boolean captchaOnOff;

    public String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        this.captchaType = captchaType;
    }

    public boolean isCaptchaOnOff() {
        return captchaOnOff;
    }

    public void setCaptchaOnOff(boolean captchaOnOff) {
        this.captchaOnOff = captchaOnOff;
    }
}
