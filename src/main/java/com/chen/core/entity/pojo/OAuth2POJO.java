package com.chen.core.entity.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * OAuth2 属性实体
 *
 * @author ： CatalpaFlat
 * @date ：Create in 22:21 2017/12/21
 */
public class OAuth2POJO {
    @Getter
    @Setter
    private List<OAuth2InsideConfigPOJO> inside;
    @Getter
    @Setter
    private OAut2hOuterConfigPOJO outer;
}
