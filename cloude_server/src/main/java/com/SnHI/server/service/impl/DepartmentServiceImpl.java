package com.SnHI.server.service.impl;

import com.SnHI.server.pojo.Department;
import com.SnHI.server.mapper.DepartmentMapper;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IDepartmentService;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门
     * @return
     */
    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment(-1);
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @Override
    public ResponseResult addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
        if (department.getResult() == 1) return new ResponseResult(200, "添加成功！");
        return new ResponseResult(500, "添加失败！");
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteDepartment(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        if (department.getResult() == -2) return new ResponseResult(500, "该部门下面有子部门，删除失败！");
        if (department.getResult() == -1) return new ResponseResult(500, "该部门有员工信息，删除失败！");
        return new ResponseResult(200,"删除成功！");
    }


}
