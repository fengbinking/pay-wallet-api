package com.ma.wallet.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.ma.wallet.core.cache.CacheKeyPrefix;
import com.ma.wallet.core.cache.RedisUtils;
import com.ma.wallet.core.constant.ResultCode;
import com.ma.wallet.core.exception.ServiceException;
import com.ma.wallet.core.utils.WebUtils;
import com.ma.wallet.core.vo.Result;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fengbin on 2017-08-10
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);
    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件
    @Value("${pay.wallet.request.key}")
    private String requestKey;

    //使用阿里 FastJson 作为JSON MessageConverter
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,//保留空的字段
                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
                SerializerFeature.WriteNullNumberAsZero);//Number null -> 0
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);
    }


    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                String message=null;
                Result result = new Result();
                if (e instanceof ServiceException) {//业务失败的异常，如“账号或密码错误”
                    e.printStackTrace();
                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                    logger.info(e.getMessage());
                } else if (e instanceof NoHandlerFoundException) {
                    result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
                } else if (e instanceof ServletException) {
                    e.printStackTrace();
                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                } else {
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                    } else {
                        message = e.getMessage();
                    }
                }
                logger.error(StringUtils.isEmpty(message)?e.getMessage():message, e);
                responseResult(response, result);
                return new ModelAndView();
            }

        });
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**");
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //接口签名认证拦截器，该签名认证比较简单，实际项目中可以使用Json Web Token或其他更好的方式替代。
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                logger.info("请求接口：{}，请求IP：{}，请求参数：{}",
                        request.getRequestURI(), WebUtils.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));
                if (!"dev".equals(env)) { //开发环境忽略签名认证
                    //验证白名单IP
                    if (WebMvcConfigurer.this.validateIpAddr(request, response) != null) {
                        return false;
                    }
                    if ("dev".equals(env)) return true;
                    //验证签名
                    boolean pass = validateSign(request);
                    if (pass) {
                        return true;
                    } else {
                        logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}",
                                request.getRequestURI(), WebUtils.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));
                        Result result = new Result();
                        result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名认证失败");
                        responseResult(response, result);
                        return false;
                    }
                } else {
                    request.setAttribute("ip", request.getParameter("ip") + "," + WebUtils.getRemoteHost(request));
                    return true;
                }
            }
        });
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 验证调用接口客户端ip是否在白名单内
     *
     * @param request
     * @param response
     * @return
     */
    private Result validateIpAddr(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        //验证白名单IP
        String ipAddr = WebUtils.getRemoteHost(request);
        if (RedisUtils.getStringFromMap(CacheKeyPrefix.WHITE_IP_MAP, ipAddr) == null) {
            logger.warn("IP不在白名单内，请求接口：{}，请求IP：{}，请求参数：{}",
                    request.getRequestURI(), WebUtils.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));
            result.setCode(ResultCode.UNAUTHORIZED).setMessage("IP[" + ipAddr + "]不在白名单内");
            responseResult(response, result);
            return result;
        }
        /**支付网关机器IP*/
        String paramsIp = request.getParameter("ip");
        if (StringUtils.isNotEmpty(paramsIp)) {
            if (RedisUtils.getStringFromMap(CacheKeyPrefix.WHITE_IP_MAP, paramsIp) == null) {
                result.setCode(ResultCode.UNAUTHORIZED).setMessage("IP[" + paramsIp + "]不在白名单内");
                responseResult(response, result);
                return result;
            }
            request.setAttribute("ip", paramsIp + "," + ipAddr);
        } else {
            result.setCode(ResultCode.UNAUTHORIZED).setMessage("ip为必传参数");
            responseResult(response, result);
        }
        if (result.getCode() == 0)
            return null;
        return result;
    }

    /**
     * 一个简单的签名认证，规则：
     * 1. 将请求参数按ascii码排序
     * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
     * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
     */
    private boolean validateSign(HttpServletRequest request) {
        String requestSign = request.getParameter("sign");//获得请求签名，如sign=19e907700db7ad91318424a97c54ed57
        if (StringUtils.isEmpty(requestSign)) {
            return false;
        }
        List<String> keys = new ArrayList<String>(request.getParameterMap().keySet());
        keys.remove("sign");//排除sign参数
        Collections.sort(keys);//排序

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String paramValue = request.getParameter(key);
            if (StringUtils.isEmpty(paramValue))//参数为空字符串就不用拼接
                continue;
            sb.append(key).append("=").append(request.getParameter(key)).append("&");//拼接字符串
        }
        String linkString = sb.toString();
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);//去除最后一个'&'
        logger.info("拦截器中待签名字段串：" + linkString);
        String sign = DigestUtils.md5Hex(linkString + requestKey);//混合密钥md5

        return StringUtils.equals(sign, requestSign);//比较
    }
}
