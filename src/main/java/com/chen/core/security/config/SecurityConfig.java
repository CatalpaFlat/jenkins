package com.chen.core.security.config;

import com.chen.constant.SpringBeanNameConstant;
import com.chen.core.security.filter.CustomAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * security 配置
 *
 * @author ： CatalpaFlat
 * @date ：Create in 21:07 2017/12/20
 */
//@Configuration
//@AutoConfigureAfter(name = SpringBeanNameConstant.DEFAULT_CUSTOM_YML_CONFIG_SERVICE_BN)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

//    @Autowired
//    private CustomAuthenticationFilter customAuthenticationFilter;

    public SecurityConfig() {
        logger.info("Loading CustomSecurityConfig ...");
    }


}
