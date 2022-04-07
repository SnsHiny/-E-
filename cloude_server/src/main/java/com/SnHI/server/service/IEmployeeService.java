package com.SnHI.server.service;

import com.SnHI.server.pojo.Employee;
import com.SnHI.server.pojo.ResponsePageResult;
import com.SnHI.server.pojo.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取所有员工信息（分页展示）
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    ResponsePageResult getAllEmployees(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取最大员工编号
     * @return
     */
    String getMaxWorkID();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    ResponseResult insertEmployee(Employee employee);

    /**
     * 获取要导出的员工数据
     * @param id
     * @return
     */
    List<Employee> getAllEmployeesToExport(Integer id);

    /**
     * 获取所有员工账套
     * @param currentPage
     * @param size
     * @return
     */
    ResponsePageResult getAllEmployeeWithSalary(Integer currentPage, Integer size);
}
