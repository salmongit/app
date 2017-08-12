package com.salmon.web.handler;

import com.salmon.domain.Result;
import com.salmon.exception.SystemException;
import com.salmon.exception.SystemExceptionCode;
import com.salmon.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof SystemException) {
            SystemException systemException = (SystemException) e;
            return ResultUtils.error(systemException.getCode(), systemException.getMessage());
        }else {
            logger.error("【系统异常】{}", e);
            return ResultUtils.error(SystemExceptionCode.AGS_TK_FLTR_ROLE_ADD_EX);
        }
    }
}
