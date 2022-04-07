package com.SnHI.server.mapper;

import com.SnHI.server.pojo.Employee;
import com.SnHI.server.pojo.ResponsePageResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工信息（分页展示）
     * @param employeePage
     * @param employee
     * @param beginDateScope
     * @return
     */
    IPage<Employee> getAllEmployees(@Param("employeePage") Page<Employee> employeePage, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 获取要导出的员工数据
     * @param id
     * @return
     */
    List<Employee> getAllEmployeesToExport(Integer id);

    /**
     * 获取所有员工账套
     * @param page
     * @return
     */
    IPage<Employee> getAllEmployeeWithSalary(Page<Employee> page);
}
