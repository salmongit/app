package com.salmon.web.controller;

import com.salmon.domain.Result;
import com.salmon.entities.IndexModel;
import com.salmon.entities.Student;
import com.salmon.exception.SystemException;
import com.salmon.exception.SystemExceptionCode;
import com.salmon.services.SearchTestService;
import com.salmon.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class TestController {

    @Autowired
    private SearchTestService searchTestService;

    @RequestMapping("/getModel")
    public List<IndexModel> getModel(){
        List<IndexModel> list =searchTestService.getIndexModelRepositoy().findByName("%中华人民共和国%","%类似变量%");
        return list;
    }

    @PostMapping(value = "/girls")
    public Result girlAdd(@Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtils.error(SystemExceptionCode.SF_USERNAME_UNAVAILABLE,bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtils.success();
    }

    @RequestMapping("/search/{keyWord}")
    public Result search(@PathVariable String keyWord){
        if ("0".endsWith(keyWord))
            throw new SystemException(SystemExceptionCode.AGS_TK_FLTR_ROLE_CK_EX);
        if ("1".endsWith(keyWord))
            throw new SystemException(SystemExceptionCode.SF_USERNAME_UNAVAILABLE, "用户名");
        return ResultUtils.success();
    }

}
