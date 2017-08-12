package com.salmon.utils;

import com.salmon.domain.Result;
import com.salmon.exception.SystemExceptionCode;


public class ResultUtils {

    public static Result info(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result info(SystemExceptionCode systemExceptionCode) {
        return info(systemExceptionCode.value(),systemExceptionCode.message());
    }

    public static Result info(SystemExceptionCode systemExceptionCode, Object ...params) {
        return info(systemExceptionCode.params(params));
    }

    public static Result success(Object object) {
        Result result = info(SystemExceptionCode.SUCCESS);
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(SystemExceptionCode systemExceptionCode) {
        return info(systemExceptionCode);
    }

    public static Result error(SystemExceptionCode systemExceptionCode, Object ...params) {
        return info(systemExceptionCode.params(params));
    }

    public static Result error(int code, String msg) {
       return info(code,msg);
    }
}
