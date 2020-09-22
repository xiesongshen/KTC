package com.ktc.user.intercept;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author XSS
 * @date 2020/8/25
 * @desc
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头上的信息
        String auth = request.getHeader("auth");

        //获取前缀
        String prefix = env.getProperty("jwt.config.prefix");

        if (auth!=null && auth.startsWith(prefix)){
            //截取token
            String token = auth.substring(prefix.length());

            //解析token
            Claims claims = jwtUtil.parseJWT(token);

            //如果携带有admin权限那么将对应的claims添加到域对象,方便controller获取
            if (claims.get("role").equals("admin")){
                request.setAttribute("admin_auth",claims);
            }

            //如果携带有user权限那么将对应的claims添加到域对象,方便controller获取
            if (claims.get("role").equals("user")){
                request.setAttribute("user_auth",claims);
            }

        }


        return true;
    }
}
