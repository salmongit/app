package com.salmon.exception;

public class SystemException extends RuntimeException {

    private int code;

    public SystemException(SystemExceptionCode exceptionCode){
        super(exceptionCode.message());
        this.code = exceptionCode.value();
    }

    public SystemException(SystemExceptionCode exceptionCode, Object... params){
        super(exceptionCode.params(params).message());
        this.code = exceptionCode.value();
    }

    public SystemException(SystemExceptionCode exceptionCode, Throwable cause){
        super(exceptionCode.message(), cause);
        this.code = exceptionCode.value();
    }

    public SystemException(SystemExceptionCode exceptionCode, Throwable cause, Object... params){
        super(exceptionCode.params(params).message(), cause);
        this.code = exceptionCode.value();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
