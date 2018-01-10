package com.chen.core.security.oauth2.token.jwt;

import com.chen.core.security.oauth2.config.CustomTokenStoreConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt  对包含信息进行拓展
 *
 * @author ： CatalpaFlat
 * @date ：Create in 22:22 2017/12/18
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    private static final Logger logger = LoggerFactory.getLogger(CustomTokenEnhancer.class);

    public CustomTokenEnhancer() {
        logger.info("Loading CustomTokenEnhancer  ...");
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        
        //附加信息
        Map<String, Object> info = new HashMap<>(1);
        info.put("author", "CatalpaFlat");
        //TODO 添加属于自己的jwt属性
        //添加并且转换
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

        return accessToken;
    }
}
