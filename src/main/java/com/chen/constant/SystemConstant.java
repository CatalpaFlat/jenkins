package com.chen.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统常量
 *
 * @author ： CatalpaFlat
 * @date ：Create in 21:24 2017/12/20
 */
public final class SystemConstant {
    private static final Logger logger = LoggerFactory.getLogger(SystemConstant.class);
    private SystemConstant() {
        logger.info("Loading SystemConstant  ...");
    }
    public static final String DEFAULT_RESPONSE_TYPE = "json";


    public static final String DEFAULT_ENDPOINT_URL_KEY = "DEFAULT_ENDPOINT_URL_KEY";
    public static final String DEFAULT_PERMIT_ALL_URL_KEY = "DEFAULT_PERMIT_ALL_URL_KEY";

    /*---------------------------------------------- STORE_TYPE ----------------------------------------------*/

    public static final String DEFAULT_STORE_TYPE_REDIS = "redis";
    public static final String DEFAULT_STORE_TYPE_JWT = "jwt";
    public static final String DEFAULT_STORE_TYPE_JDBC = "jdbc";
    public static final String DEFAULT_STORE_TYPE = "default";
}
