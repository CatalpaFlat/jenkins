package com.chen.core.entity.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * OAuth2 外层配置
 *
 * @author ： CatalpaFlat
 * @date ：Create in 16:28 2017/12/24
 */
public class OAut2hOuterConfigPOJO {

    @Getter
    @Setter
    private String jwtSigningKey = "CatalpaFlat";
    @Getter
    @Setter
    private String storeType = "jwt";
}
