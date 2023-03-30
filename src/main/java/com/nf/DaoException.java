package com.nf;

/**
 * 异常类
 * RuntimeException 运行时异常
 */
public class DaoException extends RuntimeException{
    /**
     * 空异常
     */
    public DaoException() {
        //空异常
    }

    /**
     * 异常信息
     * @param message 异常消息
     */
    public DaoException(String message) {
        //异常消息
        super(message);
    }

    /**
     * 异常信息
     * @param cause 异常栈
     */
    public DaoException(Throwable cause) {
        //异常本身
        super(cause);
    }

    /**
     * 异常信息
     * @param message 异常消息
     * @param cause 异常栈
     */
    public DaoException(String message, Throwable cause) {
        //消息异常，异常栈
        super(message, cause);
    }
}
