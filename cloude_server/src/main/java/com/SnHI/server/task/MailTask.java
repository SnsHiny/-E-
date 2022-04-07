package com.SnHI.server.task;

import com.SnHI.server.pojo.Employee;
import com.SnHI.server.pojo.MailConstants;
import com.SnHI.server.pojo.MailLog;
import com.SnHI.server.service.IEmployeeService;
import com.SnHI.server.service.IMailLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务（实现消息可靠性）
 */
@Component
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 邮件发送定时任务：10秒一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask() {
        // 状态为0且重试时间小于当前时间的需要被重新发送
        List<MailLog> mailLogs = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        mailLogs.forEach(mailLog -> {
            // 重试次数超过3次，更新为投递失败，不再重试
            if (mailLog.getCount() > 3) {
                mailLogService.update(new UpdateWrapper<MailLog>().set("status", 2).eq("msgId", mailLog.getMsgId()));
            } else {
                // 更新重试次数，更新时间，重试时间
                mailLogService.update(new UpdateWrapper<MailLog>()
                        .set("count", mailLog.getCount() + 1)
                        .set("updateTime", LocalDateTime.now())
                        .set("tryTime", LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT))
                        .eq("msgId", mailLog.getMsgId()));
                Employee employee = employeeService.getAllEmployeesToExport(mailLog.getEid()).get(0);
                rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, employee, new CorrelationData(mailLog.getMsgId()));
            }
        });
    }

}
