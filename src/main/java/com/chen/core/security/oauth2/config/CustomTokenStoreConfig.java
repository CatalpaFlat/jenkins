package com.chen.core.security.oauth2.config;

import com.chen.constant.SpringBeanNameConstant;
import com.chen.core.config.CustomYmlConfig;
import com.chen.core.security.oauth2.token.jwt.CustomTokenEnhancer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * tokenStore
 *
 * @author ： CatalpaFlat
 * @date ：Create in 20:48 2017/12/18
 */
@Configuration
public class CustomTokenStoreConfig {
    private static final Logger logger = LoggerFactory.getLogger(CustomTokenStoreConfig.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    public CustomTokenStoreConfig() {
        logger.info("Loading CustomTokenStoreConfig ...");
    }
    /**
     * redis token 配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "catalpaFlat.security.oauth2s.outer", name = "storeType", havingValue = "redis")
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * jdbc token 配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "catalpaFlat.security.oauth2s.outer", name = "storeType", havingValue = "jdbc")
    public TokenStore jdbcTokenStore() {
        Assert.state(dataSource != null, "DataSource must be provided");

        return new JdbcTokenStore(dataSource);
    }

    /**
     * jwt Token 配置, matchIfMissing = true
     *
     * @author ： CatalpaFlat
     * @date ：Create in 16:45 2017/12/22
     */
    @Configuration
    @ConditionalOnProperty(prefix = "catalpaFlat.security.oauth2s.outer", name = "storeType", havingValue = "jwt")
    public class JwtTokenConfig {

        private final Logger logger = LoggerFactory.getLogger(JwtTokenConfig.class);
        @Value("${default.jwt.signing.key}")
        private String defaultJwtSigningKey;
        @Autowired
        private CustomYmlConfig customYmlConfig;

        public JwtTokenConfig() {
            logger.info("Loading JwtTokenConfig ...");
        }

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            String jwtSigningKey = customYmlConfig.getSecurity().getOauth2s().getOuter().getJwtSigningKey();

            Assert.state(StringUtils.isNotBlank(jwtSigningKey), "jwtSigningKey is not configured");

            //秘签
            jwtAccessTokenConverter.setSigningKey(StringUtils.isBlank(jwtSigningKey) ? defaultJwtSigningKey : jwtSigningKey);
            return jwtAccessTokenConverter;
        }

        @Bean
        @ConditionalOnMissingBean(name = SpringBeanNameConstant.DEFAULT_CUSTOM_JWT_TOKEN_ENHANCER_BEAN_NAME)
        public TokenEnhancer jwtTokenEnhancer() {
            return new CustomTokenEnhancer();
        }

    }
}
