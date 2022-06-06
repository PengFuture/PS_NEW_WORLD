package com.china.world.controller.system;

import com.china.world.model.LoginBody;
import com.china.world.service.CaptChaService;
import com.china.world.service.LoginService;
import com.china.world.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制
 *
 * @author Peng
 * @date 2022/3/1 16:33
 */
@RestController
public class SystemLoginController {

    @Autowired
    private CaptChaService captChaService;
    @Autowired
    private LoginService loginService;

    @GetMapping("/captchaImage")
    public AjaxResult getVerifyCaptcha() {
        return captChaService.getVerifyCaptcha();
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        // 生成令牌
        return loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        return loginService.getInfo();
    }

}
