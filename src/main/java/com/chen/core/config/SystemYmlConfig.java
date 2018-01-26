package com.chen.core.config;

import com.chen.core.constant.SpringBeanNameConstant;
import com.chen.core.model.to.ParamTO;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 9:52 2018/1/18
 */
@Configuration(value = SpringBeanNameConstant.DEFAULT_SYSTEM_YML_CONFIG_BN)
@ConfigurationProperties(prefix = "catalpaFlat")
public class SystemYmlConfig {
    private static final Logger logger = LoggerFactory.getLogger(SystemYmlConfig.class);
    @Getter
    @Setter
    private ParamTO param;
    @Getter
    @Setter
    private String test;

    public SystemYmlConfig() {
        logger.info("Loading SystemYmlConfig...");
    }

}
