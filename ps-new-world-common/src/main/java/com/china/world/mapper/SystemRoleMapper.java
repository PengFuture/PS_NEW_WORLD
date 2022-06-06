package com.china.world.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.china.world.domain.SystemRole;

import java.util.List;

/**
 * @author Peng
 * @date 2022/3/10 11:50
 */
public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SystemRole> selectRolePermissionByUserId(Long userId);
}
