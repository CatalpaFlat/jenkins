package com.chen.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * spring bean name 常量
 *
 * @author ： CatalpaFlat
 * @date ：Create in 21:04 2017/12/20
 */
public final class SpringBeanNameConstant {
    private static final Logger logger = LoggerFactory.getLogger(SpringBeanNameConstant.class);
    private SpringBeanNameConstant() {
        logger.info("Loading SpringBeanNameConstant ...");
    }

    public static final String DEFAULT_CUSTOM_YML_CONFIG_SERVICE_BN = "customYmlConfig";

    public static final String DEFAULT_CUSTOM_JWT_TOKEN_ENHANCER_BEAN_NAME = "jwtTokenEnhancer";

    public static final String DEFAULT_CUSTOM_USER_DETAIL_SERVICE_BN = "customUserDetailsService";


    public static final String DEFAULT_CUSTOM_AUTHENTICATION_FAILURE_HANDLER_BN = "customAuthenticationFailureHandler";

    public static final String DEFAULT_CUSTOM_AUTHENTICATION_SUCCESS_HANDLER_BN =  "customAuthenticationSuccessHandler";



    public static final String DEFAULT_CUSTOM_AUTHENTICATION_LOGIN_CHECK_FILTER_BN = "customAuthenticationFilter";

}
