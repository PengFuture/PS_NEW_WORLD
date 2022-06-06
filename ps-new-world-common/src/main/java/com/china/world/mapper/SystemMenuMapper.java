package com.china.world.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.china.world.domain.SystemMenu;

import java.util.List;

/**
 * @author Peng
 * @date 2022/3/10 11:50
 */
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long userId);

}
