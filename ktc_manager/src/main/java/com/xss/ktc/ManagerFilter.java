package com.xss.ktc;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XSS
 * @date 2020/8/27
 * @desc
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private Environment env;

    @Autowired
    private JwtUtil jwtUtil;



    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();

        //获取请求头数据
        String auth = request.getHeader("auth");
        String prefix = env.getProperty("jwt.config.prefix");

        //设置响应数据的格式
        response.setContentType("text/html;charset=utf8");

        if (auth==null || !auth.startsWith(prefix)){
            //不是管理员
            //终止登陆
            context.setSendZuulResponse(false);

            //设置正确响应内容
            context.setResponseBody("无权访问");

            return null;
        }

        //获取token字符串
        String token = auth.substring(prefix.length());

        Claims claims = jwtUtil.parseJWT(token);

        if (!claims.get("role").toString().equals("admin")){
            context.setSendZuulResponse(false);

            //设置正确响应内容
            context.setResponseBody("你不是管理员哦");
        }

        return null;
    }
}
