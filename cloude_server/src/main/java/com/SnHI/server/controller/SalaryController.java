package com.SnHI.server.controller;


import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.pojo.Salary;
import com.SnHI.server.service.ISalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  工资管理接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Api(tags = "SalaryController工资账套接口")
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private ISalaryService salaryService;

    @ApiOperation("获取所有工资账套")
    @GetMapping("/")
    public List<Salary> getAllSalary() {
        return salaryService.list();
    }

    @ApiOperation("新增工资账套")
    @PostMapping("/")
    public ResponseResult addSalary(@RequestBody Salary salary) {
        salary.setCreateDate(LocalDateTime.now());
        if (salaryService.save(salary)) return new ResponseResult(200, "插入成功！");
        return new ResponseResult(500, "插入失败！");
    }

    @ApiOperation("修改工资账套")
    @PutMapping("/")
    public ResponseResult updateSalary(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) return new ResponseResult(200, "更新成功！");
        return new ResponseResult(500, "更新失败！");
    }

    @ApiOperation("删除工资账套")
    @DeleteMapping("/{id}")
    public ResponseResult deleteSalary(@PathVariable Integer id) {
        if (salaryService.removeById(id)) return new ResponseResult(200, "删除成功！");
        return new ResponseResult(500, "删除失败！");
    }

}
