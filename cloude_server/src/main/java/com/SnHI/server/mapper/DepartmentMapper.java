package com.SnHI.server.mapper;

import com.SnHI.server.pojo.Department;
import com.SnHI.server.pojo.ResponseResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

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
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartment(Integer parentId);

    /**
     * 添加部门
     * @param department
     * @return
     */
    void addDepartment(Department department);

    /**
     * 删除部门
     * @param department
     * @return
     */
    void deleteDepartment(Department department);
}
