package com.chen.core.valid;

import com.chen.core.annotation.ParameterValid;
import com.chen.core.constant.SpringBeanNameConstant;
import com.chen.core.exception.ParameterValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 14:04 2018/1/17
 */
@Component(SpringBeanNameConstant.INT_TYPE_PROCESSOR_BN)
public class IntParamValidProcessor extends AbstractParamValidProcessor {
    private static final String INT_PARAM_ERROR = "Invalid interva";
    private static final int INT_PARAM_TYPE_MAX_SIZE = 2;
    private static final int INT_PARAM_SIZE_SUBSCRIPT_MIN = 0;
    private static final int INT_PARAM_SIZE_SUBSCRIPT_MAX = 0;
    private static final Logger log = LoggerFactory.getLogger(AbstractParamValidProcessor.class);

    @Override
    public String validate(ParameterValid parameterValid, Object param) {
        int paramInt = (int) param;
        if (parameterValid.isMin() && paramInt < parameterValid.min()) {
            return super.msg;
        }
        if (parameterValid.isMax() && paramInt < parameterValid.max()) {
            return super.msg;
        }
        if (parameterValid.isSection()) {
            int[] section = parameterValid.section();
            if (section.length != INT_PARAM_TYPE_MAX_SIZE) {
                log.error(INT_PARAM_ERROR);
                throw new ParameterValidException(INT_PARAM_ERROR);
            }
            if (!(paramInt > section[INT_PARAM_SIZE_SUBSCRIPT_MIN] && paramInt < section[INT_PARAM_SIZE_SUBSCRIPT_MAX])) {
                return super.msg;
            } else if (!(paramInt > section[INT_PARAM_SIZE_SUBSCRIPT_MAX] && paramInt < section[INT_PARAM_SIZE_SUBSCRIPT_MIN])) {
                return super.msg;
            }
        }
        return null;
    }
}
