package com.bytedance.douyinclouddemo.pojo;

import lombok.Data;

@Data
public class OperaResult {
    private Integer code;
    private String msg;
    private Object data;

    public OperaResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public OperaResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public OperaResult() {
    }
}
