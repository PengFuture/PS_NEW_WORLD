package com.china.world.manager.factory;

import com.china.world.constant.Constants;
import com.china.world.domain.SystemLoginInfo;
import com.china.world.service.SystemLoginInfoService;
import com.china.world.utils.*;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.TimerTask;

/**
 * @author Peng
 * @date 2022/3/3 11:26
 */
public class AsyncFactory {

    private static final Logger logger = LoggerFactory.getLogger(AsyncFactory.class);

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLoginInfo(final String username, final String status, final String message,
                                            final Object... args) {

        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        // 获取ip地址
        final String ip = IpUtils.getIpAddress(ServletUtils.getRequest());

        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIp(ip);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(LogUtils.getBlock(ip));
                stringBuilder.append(address);
                stringBuilder.append(LogUtils.getBlock(username));
                stringBuilder.append(LogUtils.getBlock(status));
                stringBuilder.append(LogUtils.getBlock(message));
                logger.info(stringBuilder.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SystemLoginInfo info = new SystemLoginInfo();
                info.setUserName(username);
                info.setIp(ip);
                info.setLoginLocation(address);
                info.setBrowser(browser);
                info.setOs(os);
                info.setMsg(message);
                // 日志状态
                if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
                    info.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    info.setStatus(Constants.FAIL);
                }
                info.setLoginTime(LocalDateTime.now());
                // 插入数据
                SpringUtils.getBean(SystemLoginInfoService.class).insertSystemLoginInfo(info);
            }
        };
    }

}
