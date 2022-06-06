package com.china.world.service.impl;

import com.china.world.config.captcha.VerifyCodeConfig;
import com.china.world.constant.Constants;
import com.china.world.service.CaptChaService;
import com.china.world.service.RedisService;
import com.china.world.utils.AjaxResult;
import com.china.world.utils.Base64;
import com.china.world.utils.IdUtils;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Peng
 * @date 2022/3/2 10:58
 */
@Service
public class CaptChaServiceImpl implements CaptChaService {

    private final static Logger logger = LoggerFactory.getLogger(CaptChaServiceImpl.class);

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private VerifyCodeConfig verifyCodeConfig;

    @Resource
    private RedisService redisService;

    @Override
    public AjaxResult getVerifyCaptcha() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("captchaOnOff", verifyCodeConfig.isCaptchaOnOff());
        if (!verifyCodeConfig.isCaptchaOnOff()) {
            return ajax;
        }
        // 保存验证码信息
        String uuid = IdUtils.simpleUuid();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr, code = null;
        BufferedImage image = null;
        // 生成验证码
        if (Constants.CAPTCHA_CODE_MATH.equals(verifyCodeConfig.getCaptchaType())) {
            String capText = captchaProducerMath.createText();
            logger.info("计算验证码为: {}", capText);
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            logger.info("计算验证码结果为: {}", code);
            image = captchaProducerMath.createImage(capStr);
        }
        if (Constants.CAPTCHA_CODE_CHAR.equals(verifyCodeConfig.getCaptchaType())) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        redisService.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            assert image != null;
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }


}
