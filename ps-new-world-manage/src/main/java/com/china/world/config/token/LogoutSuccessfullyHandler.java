package com.china.world.config.token;

import com.alibaba.fastjson.JSON;
import com.china.world.constant.Constants;
import com.china.world.constant.HttpStatus;
import com.china.world.manager.AsyncManager;
import com.china.world.manager.factory.AsyncFactory;
import com.china.world.model.LoginUser;
import com.china.world.service.TokenService;
import com.china.world.utils.AjaxResult;
import com.china.world.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出处理
 *
 * @author Peng
 * @date 2022/3/8 9:38
 */
@Configuration
public class LogoutSuccessfullyHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (null != loginUser) {
            String userName = loginUser.getUsername();
            tokenService.deleteRedisLoginUser(loginUser.getToken());
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(userName, Constants.LOGOUT, "登出成功!"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "登出成功!")));
    }
}
