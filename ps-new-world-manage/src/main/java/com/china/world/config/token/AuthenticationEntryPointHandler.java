package com.china.world.config.token;

import com.alibaba.fastjson.JSON;
import com.china.world.constant.HttpStatus;
import com.china.world.utils.AjaxResult;
import com.china.world.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @author Peng
 * @date 2021/11/11 16:25
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", httpServletRequest.getRequestURI());
        ServletUtils.renderString(httpServletResponse, JSON.toJSONString(AjaxResult.error(HttpStatus.UNAUTHORIZED, msg)));
    }
}
