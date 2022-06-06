package com.china.world.service.impl;

import com.china.world.domain.SystemLoginInfo;
import com.china.world.mapper.SystemLoginInfoMapper;
import com.china.world.qo.SystemLoginInfoQo;
import com.china.world.service.SystemLoginInfoService;
import com.china.world.utils.TableDataInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Peng
 * @date 2022/3/3 11:50
 */
@Service
public class SystemLoginInfoImpl implements SystemLoginInfoService {

    @Resource
    private SystemLoginInfoMapper systemLoginInfoMapper;

    @Override
    public void insertSystemLoginInfo(SystemLoginInfo systemLoginInfo) {
        systemLoginInfoMapper.insert(systemLoginInfo);
    }

    @Override
    public TableDataInfo<SystemLoginInfo> selectSystemLoginInfoList(SystemLoginInfoQo qo) {
        return TableDataInfo.build(systemLoginInfoMapper.selectPage(qo, qo.wrapper()));
    }
}
