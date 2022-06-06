package com.china.world.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.china.world.domain.SystemUser;
import com.china.world.mapper.SystemUserMapper;
import com.china.world.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Peng
 * @date 2022/3/10 11:55
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public SystemUser selectUserByUserName(String userName) {
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemUser::getUserName, userName);
        return systemUserMapper.selectOne(lambdaQueryWrapper);
    }
}
