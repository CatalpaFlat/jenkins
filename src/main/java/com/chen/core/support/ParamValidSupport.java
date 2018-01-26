package com.chen.core.support;

import com.chen.core.annotation.ParameterValid;
import com.chen.core.config.SystemYmlConfig;
import com.chen.core.exception.ParameterValidException;
import com.chen.core.model.to.ParamTO;
import com.chen.core.model.to.ParamValidTO;
import com.chen.core.valid.ParamValidProcessor;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 9:59 2018/1/17
 */
public class ParamValidSupport {
    private static final Logger logger = LoggerFactory.getLogger(ParamValidSupport.class);
    private static final String PARAM_TYPE_ERROR = "param type error";
    private static ParamValidSupport mInstance;
    private Map<String, ParamValidProcessor> paramValidProcessorMap;
    private ParamTO paramTO;
    private ParamValidSupport(Map<String, ParamValidProcessor> paramValidProcessorMap, SystemYmlConfig systemYmlConfig) {
        this.paramValidProcessorMap = paramValidProcessorMap;
        if (systemYmlConfig != null){
            this.paramTO = systemYmlConfig.getParam();
        }
    }

    public static ParamValidSupport get(Map<String, ParamValidProcessor> paramValidProcessorMap, SystemYmlConfig param) {
        if (mInstance == null) {
            synchronized (ParamValidSupport.class) {
                if (mInstance == null) {
                    mInstance = new ParamValidSupport(paramValidProcessorMap,param);
                }
            }
        }
        return mInstance;
    }

    /**
     * 校验
     */
    public List<String> validate(String className, String methodName,
                                 Class<?> annotationClass, Object[] args)
            throws NotFoundException, NoSuchMethodException, ClassNotFoundException {

        if (StringUtils.isBlank(className)) {
            return null;
        }
        if (StringUtils.isBlank(methodName)) {
            return null;
        }
        if (annotationClass == null) {
            return null;
        }

        ClassPool pool = ClassPool.getDefault();
        CtClass ct = pool.get(className);
        CtMethod ctMethod = ct.getDeclaredMethod(methodName);
        Object[][] parameterAnnotations = ctMethod.getParameterAnnotations();

        List<String> errorLists = new ArrayList<>();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            Object[] parameterAnnotation = parameterAnnotations[i];
            Object param = args[i];
            for (Object object : parameterAnnotation) {
                Annotation annotation = (Annotation) object;
                Class<? extends Annotation> aClass = annotation.annotationType();
                if (aClass.equals(annotationClass)) {
                    boolean isEmpty = ((ParameterValid) object).isEmpty();
                    String type = typeCheck(param);
                    String name = type.toLowerCase() + ParamValidProcessor.class.getSimpleName();
                    if (isEmpty) {
                        ParamValidProcessor paramValidProcessor = paramValidProcessorMap.get(name);
                        boolean check = paramValidProcessor.validateType((ParameterValid) object, param);
                        if (!check) {
                            logger.error(PARAM_TYPE_ERROR);
                            errorLists.add(param + PARAM_TYPE_ERROR);
                            break;
                        } else {
                            String msg = paramValidProcessor.validate((ParameterValid) object, param);
                            if (StringUtils.isNotBlank(msg)) {
                                errorLists.add(msg);
                            }
                        }
                    }
                }
            }
        }
        if (errorLists.size() != 0) {
            return errorLists;
        }
        return null;
    }

    private String typeCheck(Object param) {
        Class<?> paramClass = param.getClass();
        String simpleName = paramClass.getSimpleName();
        List<String> types = paramTO.getTypes();
        List<ParamValidTO> valid = paramTO.getValid();
        String result = null;
        for (int i =0;i<types.size();i++){
            String str = types.get(i);
            if (str.equals(simpleName)){
                result = valid.get(i).getBeanPrefix();
                break;
            }
        }
        if (StringUtils.isBlank(result)){
            throw new ParameterValidException("处理器" + paramClass + "不存在");
        }
        return result;
    }
}
