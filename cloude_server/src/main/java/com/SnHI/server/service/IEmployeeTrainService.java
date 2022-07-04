package com.SnHI.server.service;

import com.SnHI.server.pojo.EmployeeTrain;
import com.SnHI.server.pojo.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
public interface IEmployeeTrainService extends IService<EmployeeTrain> {

    /**
     * 获取所有员工培训记录
     * @return
     */
    List<EmployeeTrain> getAllEmployeeTrain();

    /**
     * 新增员工培训记录
     * @param employeeTrain
     * @return
     */
    ResponseResult insertEmployeeTrain(EmployeeTrain employeeTrain);

}
