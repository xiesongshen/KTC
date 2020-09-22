package com.xss.web;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/**
 * @author XSS
 * @date 2020/8/27
 * @desc
 */
@Component
public class WebFilter extends ZuulFilter {

    /**
     * 执行时机
     * pre:     在进入微服网关之间执行
     * route:   在执行微服务网关时执行
     * post:    在执行微服务网关之后执行
     * error:   在执行微服务出错执行
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 执行顺序
     * 数字越大,优先级越低
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行该过滤器
     * true:执行
     * false:不执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器执行逻辑
     * @return 返回null代表放行,访问对应的微服务
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("Zuul过滤器生效啦！");
        return null;
    }
}
