package com.SnHI.server.service.impl;

import com.SnHI.server.mapper.MailLogMapper;
import com.SnHI.server.pojo.*;
import com.SnHI.server.mapper.EmployeeMapper;
import com.SnHI.server.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MailLogMapper mailLogMapper;

    /**
     * 获取所有员工信息（分页展示）
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    @Override
    public ResponsePageResult getAllEmployees(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> employeePage = new Page<>(currentPage, size);
        IPage<Employee> result = employeeMapper.getAllEmployees(employeePage, employee, beginDateScope);
        return new ResponsePageResult(result.getTotal(), result.getRecords());
    }

    /**
     * 获取最大员工编号
     * @return
     */
    @Override
    public String getMaxWorkID() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        int maxWorkID = Integer.parseInt(maps.get(0).get("max(workID)").toString()) + 1;
        return String.format("%08d",maxWorkID);
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @Override
    public ResponseResult insertEmployee(Employee employee) {
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long date = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(date / 365.00)));
        if (1 == employeeMapper.insert(employee)) {
            Employee emp = employeeMapper.getAllEmployeesToExport(employee.getId()).get(0);
            // 数据库记录发送的消息
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(emp.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            // 当前时间推后一分钟
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);
            // 发送消息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, emp, new CorrelationData(msgId));
            return new ResponseResult(200, "插入成功！");
        }
        return new ResponseResult(500, "插入失败！");
    }

    /**
     * 获取要导出的员工数据
     * @param id
     * @return
     */
    @Override
    public List<Employee> getAllEmployeesToExport(Integer id) {
        return employeeMapper.getAllEmployeesToExport(id);
    }

    /**
     * 获取所有员工账套
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public ResponsePageResult getAllEmployeeWithSalary(Integer currentPage, Integer size) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeePage = employeeMapper.getAllEmployeeWithSalary(page);
        ResponsePageResult pageResult = new ResponsePageResult(employeePage.getTotal(), employeePage.getRecords());
        return pageResult;
    }
}
