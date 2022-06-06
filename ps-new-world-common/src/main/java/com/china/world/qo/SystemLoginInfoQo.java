package com.china.world.qo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.china.world.domain.SystemLoginInfo;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Peng
 * @date 2022/3/8 11:36
 */
public class SystemLoginInfoQo extends BaseQuery<SystemLoginInfo> implements Serializable {

    private String ip;
    private String status;
    private String username;
    private String beginTime;
    private String endTime;

    @Override
    public Wrapper<SystemLoginInfo> wrapper() {
        LambdaQueryWrapper<SystemLoginInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(ip), SystemLoginInfo::getIp, ip)
                .eq(StringUtils.isNotBlank(status), SystemLoginInfo::getStatus, status)
                .like(StringUtils.isNotBlank(username), SystemLoginInfo::getUserName, username)
                .between(StringUtils.isNotBlank(beginTime) && StringUtils.isNotBlank(endTime), SystemLoginInfo::getLoginTime, beginTime, endTime)
                .orderByDesc(SystemLoginInfo::getLoginTime);
        return wrapper;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
