package com.SnHI.server.config;

import com.SnHI.server.pojo.MailConstants;
import com.SnHI.server.pojo.MailLog;
import com.SnHI.server.service.IMailLogService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {

    // 日志
    public final static Logger Logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private IMailLogService mailLogService;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /**
         * 消息确认回调，确认消息是否到达broker消息中间件
         * data：消息唯一标识
         * ack：确认结果
         * cause：失败原因
         */
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            String msgId = data.getId();
            if (ack) {
                // 消息确认成功
                Logger.info("{}====>消息发送成功", msgId);
                // 更新数据库中记录
                mailLogService.update(new UpdateWrapper<MailLog>().set("status", 1).eq("msgId", msgId));
            } else {
                Logger.error("{}====>消息发送失败", msgId);
            }
        });

        /**
         * 消息失败回调，比如router不到queue
         * msg：消息主题
         * repCode：响应码
         * repText：响应描述
         * exchange：交换机
         * routingKey：路由键
         */
        rabbitTemplate.setReturnsCallback((msg) -> {
            Logger.error("消息发送到队列时异常");
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue queue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }

}
