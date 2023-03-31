package com.nf;

/**
 *
 */
public class ReflexException extends RuntimeException{
    /**
     * 空异常
     */
    public ReflexException() {
        //空异常
    }

    /**
     * 异常信息
     * @param message 异常消息
     */
    public ReflexException(String message) {
        //异常消息
        super(message);
    }

    /**
     * 异常信息
     * @param cause 异常栈
     */
    public ReflexException(Throwable cause) {
        //异常本身
        super(cause);
    }

    /**
     * 异常信息
     * @param message 异常消息
     * @param cause 异常栈
     */
    public ReflexException(String message, Throwable cause) {
        //消息异常，异常栈
        super(message, cause);
    }
}
