package com.china.world.excetpion;

/**
 * 登录异常
 *
 * @author Peng
 * @date 2021/11/11 15:04
 */
public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String message;

    public LoginException() {
    }

    public LoginException(String message) {
        this.message = message;
    }

    public LoginException(Integer code, String message) {
        this.message = message;
    }
}
