package com.SnHI.server.service.impl;

import com.SnHI.server.mapper.EmployeeMapper;
import com.SnHI.server.pojo.Employee;
import com.SnHI.server.pojo.EmployeeRemove;
import com.SnHI.server.mapper.EmployeeRemoveMapper;
import com.SnHI.server.service.IEmployeeRemoveService;
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
public class EmployeeRemoveServiceImpl extends ServiceImpl<EmployeeRemoveMapper, EmployeeRemove> implements IEmployeeRemoveService {

    @Autowired
    private EmployeeRemoveMapper employeeRemoveMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 获取所有员工调动记录
     * @return
     */
    @Override
    public List<EmployeeRemove> getAllEmployeeRemove() {
        return employeeRemoveMapper.getAllEmployeeRemove();
    }

    /**
     * 新增员工调动记录
     * @param employeeRemove
     * @return
     */
    @Override
    public boolean insertEmployeeRemove(EmployeeRemove employeeRemove) {
        Employee employee = new Employee();
        employee.setId(employeeRemove.getEid());
        employee.setDepartmentId(employeeRemove.getAfterDepId());
        employee.setPosId(employeeRemove.getAfterPosId());
        return employeeRemoveMapper.insert(employeeRemove) == 1 && employeeMapper.updateById(employee) == 1;
    }

    /**
     * 修改员工调动记录
     * @param employeeRemove
     * @return
     */
    @Override
    public boolean updateEmployeeRemove(EmployeeRemove employeeRemove) {
        Employee employee = new Employee();
        employee.setId(employeeRemove.getEid());
        employee.setDepartmentId(employeeRemove.getAfterDepId());
        employee.setPosId(employeeRemove.getAfterPosId());
        return employeeRemoveMapper.updateById(employeeRemove) == 1 && employeeMapper.updateById(employee) == 1;
    }

}
