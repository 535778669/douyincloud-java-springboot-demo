package com.bytedance.douyinclouddemo.pojo;

public enum CodeConstant {
    SUCCESS(1, "success"),
    CREATION_FAILED(1, "数据中已有该账户"),
    FAIL(0, "失败"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "未找到"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    TRANSACTION_PASSWORD_ERROR(1, "交易密码错误"),
    ORDER_ACCEPTANCE_FAILED(0, "接单失败"),
    ASYMMETRY(0,"个人信息不符"),
    GATEWAY_TIMEOUT(504, "网关超时");

    private int code;
    private String message;

    CodeConstant(int code, String message) {
        this.code = code;
        this.message = message;
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
}
