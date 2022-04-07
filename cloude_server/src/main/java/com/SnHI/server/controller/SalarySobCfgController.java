package com.SnHI.server.controller;

import com.SnHI.server.pojo.Employee;
import com.SnHI.server.pojo.ResponsePageResult;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.pojo.Salary;
import com.SnHI.server.service.IEmployeeService;
import com.SnHI.server.service.ISalaryService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工资账套
 */
@Api(tags = "SalarySobCfgController员工账套接口")
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ISalaryService salaryService;

    @ApiOperation("获取所有员工工资账套")
    @GetMapping("/")
    public ResponsePageResult getAllEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                                       @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getAllEmployeeWithSalary(currentPage, size);
    }

    @ApiOperation("获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries() {
        return salaryService.list();
    }

    @ApiOperation("更新员工账套")
    @PutMapping("/")
    public ResponseResult updateEmployeeSalary(Integer eid, Integer sid) {
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id", eid))) {
            return new ResponseResult(200, "更新成功！");
        }
        return new ResponseResult(500, "更新失败！");
    }

}
