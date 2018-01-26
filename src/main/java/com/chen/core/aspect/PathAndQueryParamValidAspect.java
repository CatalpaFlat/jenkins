package com.chen.core.aspect;

import com.chen.core.annotation.ParameterValid;
import com.chen.core.annotation.PathAndQueryParamValid;
import com.chen.core.config.SystemYmlConfig;
import com.chen.core.model.vo.ResponseVO;
import com.chen.core.support.AdvanceResponseSupport;
import com.chen.core.support.ParamValidSupport;
import com.chen.core.valid.ParamValidProcessor;
import javassist.NotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 9:55 2018/1/17
 */
@Aspect
@Component
public class PathAndQueryParamValidAspect {

    private static final Logger log = LoggerFactory.getLogger(PathAndQueryParamValidAspect.class);

    @Autowired
    private Map<String, ParamValidProcessor> paramValidProcessorMap;
    @Autowired
    private SystemYmlConfig systemYmlConfig;

    @Before("@annotation(paramValid)")
    public void paramValid(JoinPoint joinPoint, PathAndQueryParamValid paramValid) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] param = joinPoint.getArgs();
        try {
            List<String> errorLists = ParamValidSupport.get(paramValidProcessorMap, systemYmlConfig).validate(className, methodName,
                    ParameterValid.class, param);
            if (errorLists != null) {
                AdvanceResponseSupport.advanceResponse(
                        new ResponseVO(HttpStatus.BAD_REQUEST.value(), "parameter empty", errorLists));
            }
        } catch (NotFoundException | NoSuchMethodException | ClassNotFoundException e) {
            log.error("e-name：" + e.getClass().getName() + "： message：" + e.getMessage());
        }
    }
}
