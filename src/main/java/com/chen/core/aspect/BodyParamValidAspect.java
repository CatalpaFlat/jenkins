package com.chen.core.aspect;

import com.chen.core.annotation.BodyParamValid;
import com.chen.core.model.vo.ResponseVO;
import com.chen.core.support.AdvanceResponseSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 15:37 2018/1/16
 */
@Aspect
@Component
public class BodyParamValidAspect {

    private static final Logger log = LoggerFactory.getLogger(BodyParamValidAspect.class);

    @Before("@annotation(paramValid)")
    public void paramValid(JoinPoint point, BodyParamValid paramValid) {
        Object[] paramObj = point.getArgs();
        if (paramObj.length > 0) {
            if (paramObj[1] instanceof BindingResult) {
                BindingResult result = (BindingResult) paramObj[1];
                ResponseVO errorMap = this.validRequestParams(result);
                if (errorMap != null) {
                    AdvanceResponseSupport.advanceResponse(errorMap);
                }
            }
        }
    }

    /**
     * 校验
     */
    private ResponseVO validRequestParams(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            List<String> lists = new ArrayList<>();
            for (ObjectError objectError : allErrors) {
                lists.add(objectError.getDefaultMessage());
            }
            return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "parameter empty", lists);
        }
        return null;
    }

}

