package com.SnHI.server.service.impl;

import com.SnHI.server.pojo.EmployeeTrain;
import com.SnHI.server.mapper.EmployeeTrainMapper;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IEmployeeTrainService;
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
public class EmployeeTrainServiceImpl extends ServiceImpl<EmployeeTrainMapper, EmployeeTrain> implements IEmployeeTrainService {

    @Autowired
    private EmployeeTrainMapper employeeTrainMapper;

    @Override
    public List<EmployeeTrain> getAllEmployeeTrain() {
        return employeeTrainMapper.getAllEmployeeTrain();
    }

    /**
     * 新增员工培训记录
     * @param employeeTrain
     * @return
     */
    @Override
    public ResponseResult insertEmployeeTrain(EmployeeTrain employeeTrain) {
        if (employeeTrain.getRemark() == "false") employeeTrain.setRemark("未完成");
        else employeeTrain.setRemark("已完成");
        if (1 == employeeTrainMapper.insert(employeeTrain)) return new ResponseResult(200, "新增成功！");
        return new ResponseResult(500, "新增失败！");
    }
}
