package com.SnHI.server.config;

import com.SnHI.server.pojo.LoginAdmin;
import com.SnHI.server.utils.JwtUtil;
import com.SnHI.server.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置类
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private RedisCache redisCache;

    /**
     * 添加Endpoint，在网页可以通过webSocket连接上服务，也就是配置webSocket的服务地址，并且可以指定是否使用socketJS
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * addEndpoint("/ws/ep")：将/ws/ep路径注册为stomp的端点，用户连接了这个端点就可以进行webSocket通讯，支持socketJS
         * setAllowedOrigins("*")：允许跨域
         * withSockJS()：支持socketJS访问
         */
        registry.addEndpoint("/ws/ep").setAllowedOriginPatterns("*").withSockJS();
    }

    /**
     * 输入通道参数配置
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                // 判断是否为连接，如果是，需要获取token，并且设置用户对象
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String token = accessor.getFirstNativeHeader("Auth-Token");
                    if (token != null) {
                        LoginAdmin loginAdmin = null;
                        try {
                            String userId = JwtUtil.parseJWT(token).getSubject();
                            if (userId != null) {
                                loginAdmin = redisCache.getCacheObject("login:" + userId);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginAdmin, null, loginAdmin.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        accessor.setUser(authenticationToken);
                    }
                }
                return message;
            }
        });
    }

    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置代理域，可以配置多个，配置代理目的地前缀为/queue，可以在配置域上向客户端推送消息
        registry.enableSimpleBroker("/queue");
    }
}
