package com.chen.core.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ： CatalpaFlat
 * @date ：Create in 11:30 2018/1/19
 */
@Component
public class SfConstant {
    /**
     * 顺丰同城 开发环境域名
     */
    public static String SF_SAME_DOMAIN;

    /**
     * 顺丰同城 生产环境的获取公共token的账号用户名
     */
    public static String SF_SAME_COMMON_TOKEN_USERNAME;

    /**
     * 顺丰同城 生产环境的获取公共token的账号密码
     */
    public static String SF_SAME_COMMON_TOKEN_PASSWORD;


    @Value("${sf-api.domain.same}")
    public  void setSF_SAME_DOMAIN(String sfSameDomain) {
        SF_SAME_DOMAIN = sfSameDomain;
    }

    @Value("${sf-api.same.common.username}")
    public  void setSfSameCommonTokenUsername(String sfSameCommonTokenUsername) {
        SF_SAME_COMMON_TOKEN_USERNAME = sfSameCommonTokenUsername;
    }

    @Value("${sf-api.same.common.password}")
    public  void setSfSameCommonTokenPassword(String sfSameCommonTokenPassword) {
        SF_SAME_COMMON_TOKEN_PASSWORD = sfSameCommonTokenPassword;
    }
}
