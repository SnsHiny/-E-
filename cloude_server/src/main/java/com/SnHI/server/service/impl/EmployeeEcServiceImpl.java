package com.SnHI.server.service.impl;

import com.SnHI.server.pojo.EmployeeEc;
import com.SnHI.server.mapper.EmployeeEcMapper;
import com.SnHI.server.service.IEmployeeEcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Service
public class EmployeeEcServiceImpl extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements IEmployeeEcService {

    @Autowired
    private EmployeeEcMapper employeeEcMapper;

    /**
     * 获取所有员工奖惩记录
     * @return
     */
    @Override
    public List<EmployeeEc> getAllEmployeeEc() {
        return employeeEcMapper.getAllEmployeeEc();
    }
}
