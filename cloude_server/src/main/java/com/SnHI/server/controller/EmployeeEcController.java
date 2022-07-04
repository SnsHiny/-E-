package com.SnHI.server.controller;

import com.SnHI.server.pojo.EmployeeEc;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IEmployeeEcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  员工奖惩管理接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Api(tags = "EmployeeEcController员工奖惩管理接口")
@RestController
@RequestMapping("/employee/ec")
public class EmployeeEcController {

    @Autowired
    private IEmployeeEcService employeeEcService;

    @ApiOperation("获取所有员工奖惩记录")
    @GetMapping("/")
    public List<EmployeeEc> getAllEmployeeEc() {
        return employeeEcService.getAllEmployeeEc();
    }

    @ApiOperation("新增员工奖惩记录")
    @PostMapping("/")
    public ResponseResult insertEmployeeEc(@RequestBody EmployeeEc employeeEc) {
        if (employeeEcService.save(employeeEc)) return new ResponseResult(200, "新增成功！");
        return new ResponseResult(500, "新增失败！");
    }

    @ApiOperation("修改员工奖惩记录")
    @PutMapping("/")
    public ResponseResult updateEmployeeEc(@RequestBody EmployeeEc employeeEc) {
        if (employeeEcService.updateById(employeeEc)) return new ResponseResult(200, "修改成功！");
        return new ResponseResult(500, "修改失败！");
    }

    @ApiOperation("删除员工奖惩记录")
    @DeleteMapping("/{id}")
    public ResponseResult deleteEmployeeEc(@PathVariable Integer id) {
        if (employeeEcService.removeById(id)) return new ResponseResult(200, "删除成功！");
        return new ResponseResult(500, "删除失败！");
    }

    @ApiOperation("批量删除员工奖惩记录")
    @DeleteMapping("/")
    public ResponseResult BatchDeleteEmployeeEc(Integer[] ids) {
        if (employeeEcService.removeByIds(Arrays.asList(ids))) return new ResponseResult(200, "批量删除成功！");
        return new ResponseResult(500, "批量删除失败！");
    }

}
