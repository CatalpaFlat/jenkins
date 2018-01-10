package com.chen.core.security.oauth2.config;

import com.chen.constant.SystemConstant;
import com.chen.core.config.CustomYmlConfig;
import com.chen.core.entity.pojo.OAuth2InsideConfigPOJO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

import static com.chen.constant.SystemConstant.*;

/**
 * 认证服务器
 *
 * @author ： CatalpaFlat
 * @date ：Create in 22:19 2017/12/21
 */
@Configuration
@EnableAuthorizationServer
public class CustomAuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationServerConfig.class);
    @Value("${oauth2.store.type}")
    private String defaultStoreType;

    @Autowired(required = false)
    private TokenStore jwtTokenStore;

    @Autowired(required = false)
    private TokenStore redisTokenStore;

    @Autowired(required = false)
    private TokenStore jdbcTokenStore;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private CustomYmlConfig customYmlConfig;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;


    public CustomAuthenticationServerConfig() {
        logger.info("Loading CustomAuthenticationServerConfig ...");
    }

    /**
     * 端点（处理入口）
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(customUserDetailsService);

        String storeType = customYmlConfig.getSecurity().getOauth2s().getOuter().getStoreType();
        storeType = StringUtils.isBlank(storeType)?defaultStoreType:storeType;

        switch (storeType){
            case DEFAULT_STORE_TYPE:
                break;
            case DEFAULT_STORE_TYPE_REDIS:
                endpoints.tokenStore(redisTokenStore);
                break;
            case DEFAULT_STORE_TYPE_JDBC:
                endpoints.tokenStore(jdbcTokenStore);
                break;
            case DEFAULT_STORE_TYPE_JWT:
                endpoints.tokenStore(jwtTokenStore);
                break;
            default:
               break;
        }
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> tokenEnhancerList = new ArrayList<>();
            tokenEnhancerList.add(jwtTokenEnhancer);
            tokenEnhancerList.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(tokenEnhancerList);
            endpoints
                    .tokenEnhancer(tokenEnhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * 服务端-clients 配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        List<OAuth2InsideConfigPOJO> insideOAuth2s = customYmlConfig.getSecurity().getOauth2s().getInside();

        InMemoryClientDetailsServiceBuilder inMemoryClientDetailsServiceBuilder = clients.inMemory();

        if (CollectionUtils.isNotEmpty(insideOAuth2s)) {
            for (OAuth2InsideConfigPOJO insideOAuth2 : insideOAuth2s) {
                inMemoryClientDetailsServiceBuilder.withClient(insideOAuth2.getClientId())
                        .secret(insideOAuth2.getClientSecret())
                        .accessTokenValiditySeconds(insideOAuth2.getTokenValiditySeconds())
                        .authorizedGrantTypes(insideOAuth2.getGrantType())
                        .scopes(insideOAuth2.getScopes());
            }
        }
    }
}
