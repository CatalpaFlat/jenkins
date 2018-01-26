package com.chen.core.valid;

import com.chen.core.annotation.ParameterValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 14:01 2018/1/17
 */
public abstract class AbstractParamValidProcessor implements ParamValidProcessor {

    private static final String PARAM_TYPE_ERROR = "param type error";
    private static final Logger logger = LoggerFactory.getLogger(AbstractParamValidProcessor.class);

    protected Class<?> paramClass;
    protected String msg;

    @Override
    public boolean validateType(ParameterValid parameterValid, Object param) {
        Class<?> type = parameterValid.type();
        Class<?> paramClass = param.getClass();
        this.paramClass = paramClass;
        this.msg = parameterValid.msg();
        if (!type.equals(paramClass)) {
            logger.error(PARAM_TYPE_ERROR);
            return false;
        }
        return true;
    }

    /**
     * 处理器校验
     * @param parameterValid 注解
     * @param param 参数
     * @return 校验结果
     */
    @Override
    public abstract String validate(ParameterValid parameterValid, Object param);
}
