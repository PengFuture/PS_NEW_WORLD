package com.china.world.qo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author Peng
 * @date 2022/3/9 11:33
 */
public abstract class BaseQuery<T> extends Page<T> {
    
    /**
     * 读取查询条件
     *
     * @return wrapper
     */
    abstract public Wrapper<T> wrapper();
}
