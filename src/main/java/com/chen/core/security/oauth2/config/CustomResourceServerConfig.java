package com.chen.core.security.oauth2.config;

import com.chen.core.config.CustomYmlConfig;
import com.chen.core.entity.pojo.AntMatchersPOJO;
import com.chen.core.entity.pojo.InterceptPOJO;
import com.chen.core.entity.to.CustomSecurityAttributeTO;
import com.chen.core.security.filter.CustomAuthenticationFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

/**
 * 资源获取服务器
 *
 * @author ： CatalpaFlat
 * @date ：Create in 17:09 2017/12/24
 */
@Configuration
@EnableResourceServer
public class CustomResourceServerConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CustomResourceServerConfig.class);


    @Autowired
    private CustomYmlConfig customYmlConfig;

    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;

    public CustomResourceServerConfig() {
        logger.info("Loading CustomResourceServerConfig  ...");
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {


        CustomSecurityAttributeTO security = customYmlConfig.getSecurity();
        AntMatchersPOJO antMatchers = security.getAntMatchers();
        String[] permitAll = antMatchers.getPermitAll();
        List<InterceptPOJO> intercepts = antMatchers.getIntercept();

        http.addFilterBefore(customAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class);

        http
            //关闭跨站维护
            .csrf()
            .disable();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config = http.authorizeRequests();


        if (ArrayUtils.isNotEmpty(permitAll)) {
            boolean singleValue = false;
            if (permitAll.length == 1) {
                if (StringUtils.isBlank(permitAll[0])) {
                    singleValue = true;
                }
            }
            //认证
            if (!singleValue) {
                config
                        .antMatchers(permitAll)
                        .permitAll();
            }
        }


        if (CollectionUtils.isNotEmpty(intercepts)) {
            for (InterceptPOJO intercept : intercepts) {
                config.antMatchers(intercept.getHttpMethod(), intercept.getUrl()).
                        hasRole(intercept.getRole());
            }
        }
        config.anyRequest()//任何请求
                .authenticated();//都需要身份认证

    }
}
