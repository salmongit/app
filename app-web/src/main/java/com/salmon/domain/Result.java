package com.salmon.domain;

import java.io.Serializable;

public class Result implements Serializable {

    /** 错误码. */
    private int code;

    /** 提示信息. */
    private String msg;

    /** 具体的内容. */
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
