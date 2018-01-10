package com.chen.core.security.filter;

import com.chen.constant.SpringBeanNameConstant;
import com.chen.constant.SystemConstant;
import com.chen.core.config.CustomYmlConfig;
import com.chen.core.exception.CustomException;
import com.chen.core.http.utils.ResponseUtil;
import com.chen.core.http.vo.Response;
import com.chen.core.security.entity.ParseTokenPOJO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义认证过滤器
 *
 * @author ： CatalpaFlat
 * @date ：Create in 10:05 2017/12/25
 */
@Component(SpringBeanNameConstant.DEFAULT_CUSTOM_AUTHENTICATION_LOGIN_CHECK_FILTER_BN)
public class CustomAuthenticationFilter extends OncePerRequestFilter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class.getName());

    @Value("${endpoints.urls}")
    private String[] endpointsUrls;
    @Value("${default.jwt.signing.key}")
    private String defaultJwtSigningKey;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomYmlConfig customYmlConfig;


    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    /**
     * 存放所有不需要认证的url
     */
    private Map<String, String> urlMap = new HashMap<>();

    public CustomAuthenticationFilter() {
        logger.info("Loading CustomAuthenticationFilter  ...");
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        for (String url : endpointsUrls) {
            urlMap.put(url, SystemConstant.DEFAULT_ENDPOINT_URL_KEY);
        }

        String[] permitAll = customYmlConfig.getSecurity().getAntMatchers().getPermitAll();
        for (String url : permitAll) {
            urlMap.put(url, SystemConstant.DEFAULT_PERMIT_ALL_URL_KEY);
        }
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (checkUrls(request)) {
            logger.info("is permit url:" + request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        String authentication = request.getHeader("Authorization");

        if (StringUtils.isBlank(authentication)) {

            logger.error("authentication is empty:" + authentication);
//            responseErrorMsg(response, HttpStatus.NOT_ACCEPTABLE.value(), "Request header without Authentication information", null);

            throw new CustomException(HttpStatus.NOT_ACCEPTABLE.value(),"cuowu");
        }


        String token = StringUtils.substringAfter(authentication, "Bearer ");

        if (StringUtils.isBlank(token)) {
            logger.error("token is empty:" + token);
            responseErrorMsg(response, HttpStatus.NOT_ACCEPTABLE.value(), "Token is empty", null);
            return;
        }


        ParseTokenPOJO parseTokenPOJO = parseToken(token);

        if (!parseTokenPOJO.isPass()) {
            responseErrorMsg(response, HttpStatus.NOT_ACCEPTABLE.value(), "Token is invalid", parseTokenPOJO.getMsg());
            return;
        }
        //let it go
        chain.doFilter(request, response);

    }

    /**
     * 校验token
     */
    private ParseTokenPOJO parseToken(String token) {

        String storeType = customYmlConfig.getSecurity().getOauth2s().getOuter().getStoreType();
        if (StringUtils.equals(storeType, SystemConstant.DEFAULT_STORE_TYPE_JWT)) {
            return parseJwtToken(token);
        }

        return new ParseTokenPOJO(true, null);
    }

    /**
     * 校验jwttoken
     */
    private ParseTokenPOJO parseJwtToken(String token) {
        String jwtSigningKey = customYmlConfig.getSecurity().getOauth2s().getOuter().getJwtSigningKey();
        String key = StringUtils.isBlank(jwtSigningKey) ? defaultJwtSigningKey : jwtSigningKey;

        Claims body;
        try {
            body = Jwts.parser().setSigningKey(key.getBytes("UTF-8")).parseClaimsJws(token).getBody();
            logger.info("Claims:" + body.toString());

            return new ParseTokenPOJO(true, body);
        } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException | UnsupportedEncodingException | IllegalArgumentException e) {
            /*
             * ExpiredJwtException 过期的jwt
             * SignatureException 签名异常
             * UnsupportedJwtException 不被支持的jwt
             * MalformedJwtException jwt为畸形的，不正确
             * UnsupportedEncodingException 不支持的编码
             * IllegalArgumentException 非法参数
             */
            logger.error("Claims error e:" + e.getClass());
            logger.error("Claims error:" + e.getMessage());
            return new ParseTokenPOJO(false, e.getMessage());
        }
    }

    /**
     * 响应异常
     */
    private void responseErrorMsg(HttpServletResponse response, int code, String errorMsg, Object object) {
        response.setStatus(code);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            Response error;
            if (object == null) {
                error = ResponseUtil.error(code, errorMsg);
            } else {
                error = ResponseUtil.error(code, errorMsg, object);
            }
            response.getWriter().write(objectMapper.writeValueAsString(error));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验urls
     */
    private boolean checkUrls(HttpServletRequest request) {
        boolean result = false;
        String requestURI = request.getRequestURI();

        Set<String> urls = urlMap.keySet();

        for (String url : urls) {
            if (pathMatcher.match(url, requestURI)) {
                result = true;
            }
        }
        return result;
    }

}
