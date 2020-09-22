package com.xss.entity;

/**
 * @author XSS
 * @date 2020/8/18
 * @desc  响应状态码
 */
public class StatusCode {

    //成功
    public static final Integer OK=20000;

    //失败
    public static final Integer ERROR=20001;

    //用户名或密码错误
    public static final Integer USER_PASSWORD_ERROR=20002;

    //权限不足
    public static final Integer ACCESS_ERROR=20003;

    //远程调用失败
    public static final Integer REMOTE_ERROR=20004;

    //重复操作
    public static final Integer REPEAT_ERROR=20005;
}
