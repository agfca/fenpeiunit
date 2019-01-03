package com.example.fenpeiunit.exception;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @ClassName SYSException
 * @Description 系统异常
 */
public class SYSException extends Exception {
    private static final long serialVersionUID = 1L;
    /**错误描述*/
    private String msg;

    public SYSException() {
        super();
    }
    public SYSException(Throwable t) {
        super(t);
    }
    public SYSException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
