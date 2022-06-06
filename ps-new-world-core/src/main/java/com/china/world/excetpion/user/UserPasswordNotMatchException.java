package com.china.world.excetpion.user;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author Peng
 * @date 2022/3/4 15:37
 */
public class UserPasswordNotMatchException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
