package com.china.world.config.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Peng
 * @date 2021/11/9 9:08
 */
@Configuration
public class TokenConfig {

    private final long millisSeconds = 1000;

    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${token.expireTime}")
    private long expireTime;

    public long getMillisSeconds() {
        return millisSeconds;
    }

    public long getMillisMinute() {
        return 60 * millisSeconds;
    }

    public Long getMillisMinuteTen() {
        return 20 * 60 * 1000L;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
