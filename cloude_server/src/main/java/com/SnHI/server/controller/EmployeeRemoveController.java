package com.SnHI.server.controller;


import com.SnHI.server.pojo.EmployeeRemove;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IEmployeeRemoveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  员工调动管理接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Api(tags = "EmployeeRemoveController员工调用管理接口")
@RestController
@RequestMapping("/employee/remove")
public class EmployeeRemoveController {

    @Autowired
    private IEmployeeRemoveService employeeRemoveService;

    @ApiOperation("获取所有员工调动记录")
    @GetMapping("/")
    public List<EmployeeRemove> getAllEmployeeRemove() {
        return employeeRemoveService.getAllEmployeeRemove();
    }

    @ApiOperation("新增员工调动记录")
    @PostMapping("/")
    public ResponseResult insertEmployeeRemove(@RequestBody EmployeeRemove employeeRemove) {
        if (employeeRemoveService.insertEmployeeRemove(employeeRemove)) return new ResponseResult(200, "新增成功！");
        return new ResponseResult(500, "新增失败！");
    }

    @ApiOperation("修改员工调动记录")
    @PutMapping("/")
    public ResponseResult updateEmployeeRemove(@RequestBody EmployeeRemove employeeRemove) {
        if (employeeRemoveService.updateEmployeeRemove(employeeRemove)) return new ResponseResult(200, "修改成功！");
        return new ResponseResult(500, "修改失败！");
    }

}
