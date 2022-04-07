package com.SnHI.server.filter;

import com.SnHI.server.pojo.LoginAdmin;
import com.SnHI.server.utils.JwtUtil;
import com.SnHI.server.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 从请求头中获取token
        String token = request.getHeader("token");
        if (token == null) {
            // 放行，让后续的过滤器拦截处理
            filterChain.doFilter(request, response);
            // 后续过滤器处理完毕返回时不继续执行下面代码
            return;
        }

        // 解析token拿到userID
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        // 根据userID从redis中获取用户信息
        String redisKey = "login:" + userId;
        LoginAdmin loginAdmin = redisCache.getCacheObject(redisKey);
        if (loginAdmin == null) {
            throw new RuntimeException("用户未登录");
        }

        // 获取权限信息封装到Authentication中
        // 将authentication存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginAdmin, null, loginAdmin.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }

}
