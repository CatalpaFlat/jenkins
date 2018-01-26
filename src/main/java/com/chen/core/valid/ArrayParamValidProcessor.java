package com.chen.core.valid;

import com.chen.core.annotation.ParameterValid;
import com.chen.core.constant.SpringBeanNameConstant;
import org.springframework.stereotype.Component;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 14:19 2018/1/17
 */
@Component(SpringBeanNameConstant.ARRAY_TYPE_PROCESSOR_BN)
public class ArrayParamValidProcessor extends AbstractParamValidProcessor {
    @Override
    public String validate(ParameterValid parameterValid, Object param) {
        String[] paramStr = (String[]) param;
        if (paramStr.length < 1) {
            return super.msg;
        }
        return null;
    }
}
