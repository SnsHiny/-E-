package com.SnHI.server.controller;

import com.SnHI.server.pojo.ChatMsg;
import com.SnHI.server.pojo.LoginAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * <p>
 *  聊天管理接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg) {
        LoginAdmin loginAdmin = (LoginAdmin)authentication.getPrincipal();
        chatMsg.setFrom(loginAdmin.getUsername());
        chatMsg.setFromNickName(loginAdmin.getAdmin().getName());
        chatMsg.setDate(LocalDateTime.now());
        // 发送消息
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
    }

}
