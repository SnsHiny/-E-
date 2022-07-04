package com.SnHI.server.service;

import com.SnHI.server.pojo.EmployeeRemove;
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
public interface IEmployeeRemoveService extends IService<EmployeeRemove> {

    /**
     * 获取所有员工调动记录
     * @return
     */
    List<EmployeeRemove> getAllEmployeeRemove();

    /**
     * 新增员工调动记录
     * @param employeeRemove
     * @return
     */
    boolean insertEmployeeRemove(EmployeeRemove employeeRemove);

    /**
     * 修改员工调动记录
     * @param employeeRemove
     * @return
     */
    boolean updateEmployeeRemove(EmployeeRemove employeeRemove);

}
