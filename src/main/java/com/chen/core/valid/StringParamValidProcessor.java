package com.chen.core.valid;

import com.chen.core.annotation.ParameterValid;
import com.chen.core.constant.SpringBeanNameConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 14:17 2018/1/17
 */
@Component(SpringBeanNameConstant.STRING_TYPE_PROCESSOR_BN)
public class StringParamValidProcessor extends AbstractParamValidProcessor {
    private static final int STRING_SIZE = 2;
    private static final char STRING_TYPE_END = '}';
    private static final char STRING_TYPE_BEGIN = '{';
    private static final char STRING_EMPTY_DOUBLE_CHARACTER = '"';
    private static final char STRING_EMPTY_SINGLE_CHARACTER = '\'';

    @Override
    public String validate(ParameterValid parameterValid, Object param) {
        String paramStr = (String) param;
        if (parameterValid.isNull()) {
            if (StringUtils.isEmpty(paramStr)) {
                return super.msg;
            }
        } else {
            if (parameterValid.isBlank()) {
                if (StringUtils.isBlank(paramStr)) {
                    return super.msg;
                } else {
                    int length = paramStr.length();
                    char begin = paramStr.charAt(0);
                    char end = paramStr.charAt(length - 1);
                    if (STRING_TYPE_BEGIN == begin &&
                            STRING_TYPE_END == end) {
                        return super.msg;
                    }
                    if (length == STRING_SIZE && STRING_EMPTY_DOUBLE_CHARACTER == begin
                            && STRING_EMPTY_DOUBLE_CHARACTER == end) {
                        return super.msg;
                    }
                    if (length == STRING_SIZE && STRING_EMPTY_SINGLE_CHARACTER == begin
                            && STRING_EMPTY_SINGLE_CHARACTER == end) {
                        return super.msg;
                    }
                }
            }
        }
        return null;
    }
}
