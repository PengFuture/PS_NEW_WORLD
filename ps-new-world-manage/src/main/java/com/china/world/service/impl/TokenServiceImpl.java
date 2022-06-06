package com.china.world.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.china.world.config.token.TokenConfig;
import com.china.world.constant.Constants;
import com.china.world.model.LoginUser;
import com.china.world.service.RedisService;
import com.china.world.service.TokenService;
import com.china.world.utils.AddressUtils;
import com.china.world.utils.IdUtils;
import com.china.world.utils.IpUtils;
import com.china.world.utils.ServletUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Peng
 * @date 2022/3/4 14:41
 */
@Service
public class TokenServiceImpl implements TokenService {

    private final static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private TokenConfig tokenConfig;
    @Autowired
    private RedisService redisService;

    @Override
    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUuid();
        loginUser.setToken(token);
        // 设置用户代理信息
        setUserAgent(loginUser);
        // 刷新令牌有效期
        refreshToken(loginUser);
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotBlank(token)) {
            try {
                Claims claims = parseToken(token);
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String redisUserKey = getTokenKey(uuid);
                return redisService.getCacheObject(redisUserKey);
            } catch (Exception e) {
                logger.error("异常信息捕捉: {}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long nowTime = System.currentTimeMillis();
        if (nowTime - expireTime <= tokenConfig.getMillisMinuteTen()) {
            refreshToken(loginUser);
        }
    }

    @Override
    public void deleteRedisLoginUser(String token) {

    }

    /**
     * 获取请求携带的令牌
     *
     * @param request 请求中的令牌
     * @return 令牌
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(tokenConfig.getHeader());
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 解析token 获取用户信息
     *
     * @param token token
     * @return 用户信息
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddress(ServletUtils.getRequest());
        loginUser.setIp(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIp(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + tokenConfig.getExpireTime() * tokenConfig.getMillisMinute());
        // 根据uuid将loginUser缓存 获取存入redis中的key
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, tokenConfig.getExpireTime(), TimeUnit.MINUTES);
    }

    /**
     * 获取redis中用户的登录key
     *
     * @param uuid 随机数
     * @return key
     */
    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, tokenConfig.getSecret()).compact();
    }

}
