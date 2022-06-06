package com.china.world.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.china.world.domain.SystemUser;

/**
 * @author Peng
 * @date 2022/3/4 15:39
 */
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 修改用户最后一次登录ip和时间
     *
     * @param systemUser 用户信息
     */
    void updateIpAndTimeById(SystemUser systemUser);
}
