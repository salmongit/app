package com.salmon.exception;

import java.util.ResourceBundle;

public enum SystemExceptionCode implements ExceptionCode {
    SUCCESS(0),
    SF_CREDENTIAL_NOT_PROVIDED(7000),
    SF_INVALID_CREDENTIAL(7001),
    SF_MISSING_USER_ROLES(7002),
    SF_INVALID_WEB_ADAPTOR_IP(7003),
    SF_USERNAME_UNAVAILABLE(7004),
    AGS_TK_FLTR_ROLE_ADD_EX(7005),
    AGS_TK_FLTR_ROLE_CK_EX(7006),
    SF_USER_OR_ROLE_MISSING(7007),
    WSM_UNABLE_TO_GET_SECURITY_CFG(7008),
    WSM_UNABLE_TO_INIT_SECURITY_CFG(7009),
    WSM_REQUEST_RESPONSE_NULL(7010);

    private ExceptionCodeImpl logCode;
    private static final ResourceBundle resourceBundle;

    SystemExceptionCode(int value) {
        this.logCode = new ExceptionCodeImpl(value, this.toString());
    }

    @Override
    public int value() {
        return this.logCode.value();
    }

    @Override
    public String message() {
        return this.logCode.message(resourceBundle);
    }

    public SystemExceptionCode params(Object... params) {
        this.logCode.params(params);
        return this;
    }

    static {
        resourceBundle = ResourceBundle.getBundle(SystemExceptionCode.class.getPackage().getName() + ".messages");
    }
}
