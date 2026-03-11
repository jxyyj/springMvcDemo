package com.yyj.springmvc.common;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 构造方法
     */
    public Result() {
    }

    /**
     * 构造方法
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     */
    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return {@link Result }
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    /**
     * 成功
     *
     * @param data    数据
     * @param message 消息
     * @return {@link Result }
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(200, message, data);
    }

    /**
     * 错误
     *
     * @param message 消息
     * @return {@link Result }
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * 错误
     *
     * @param code    状态码
     * @param message 消息
     * @return {@link Result }
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
