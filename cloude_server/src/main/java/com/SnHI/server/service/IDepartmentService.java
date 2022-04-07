package com.SnHI.server.service;

import com.SnHI.server.pojo.Department;
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
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartment();

    /**
     * 添加部门
     * @param department
     * @return
     */
    ResponseResult addDepartment(Department department);

    /**
     * 删除部门
     * @param id
     * @return
     */
    ResponseResult deleteDepartment(Integer id);
}
