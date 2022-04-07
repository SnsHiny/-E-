package com.SnHI.server.controller;


import com.SnHI.server.pojo.Department;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@RestController
@Api(tags = "DepartmentController部门接口")
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping("/")
    @ApiOperation(value = "获取所有部门")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/")
    @ApiOperation(value = "添加部门")
    public ResponseResult addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除部门")
    public ResponseResult deleteDepartment(@PathVariable Integer id) {
        return departmentService.deleteDepartment(id);
    }
}
