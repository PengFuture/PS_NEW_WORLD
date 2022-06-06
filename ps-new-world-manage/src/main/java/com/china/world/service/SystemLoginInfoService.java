package com.china.world.service;

import com.china.world.domain.SystemLoginInfo;
import com.china.world.qo.SystemLoginInfoQo;
import com.china.world.utils.TableDataInfo;

/**
 * 系统访问日志服务层
 *
 * @author Peng
 * @date 2022/3/3 11:50
 */
public interface SystemLoginInfoService {

    /**
     * 新增系统登录日志
     *
     * @param systemLoginInfo 访问日志对象
     */
    void insertSystemLoginInfo(SystemLoginInfo systemLoginInfo);

    /**
     * 查询系统登录日志集合
     *
     * @param qo 访问日志对象
     * @return 登录记录集合
     */
    TableDataInfo<SystemLoginInfo> selectSystemLoginInfoList(SystemLoginInfoQo qo);

}
