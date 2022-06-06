package com.china.world.excetpion.user;

import com.china.world.excetpion.BaseException;

/**
 * 用户信息异常类
 *
 * @author Peng
 * @date 2022/3/4 11:20
 */
public class UserException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }

}
