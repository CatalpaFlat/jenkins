package com.chen.core.entity.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * OAuth2 内层配置
 *
 * @author ： CatalpaFlat
 * @date ：Create in 16:28 2017/12/24
 */
public class OAuth2InsideConfigPOJO {

    @Getter
    @Setter
    private String clientId;
    @Getter
    @Setter
    private String clientSecret;
    @Getter
    @Setter
    private Integer tokenValiditySeconds;
    @Getter
    @Setter
    private String[] grantType;
    @Getter
    @Setter
    private String[] scopes;
    @Getter
    @Setter
    private String jwtSigningKey;
    @Getter
    @Setter
    private String storeType;
}
