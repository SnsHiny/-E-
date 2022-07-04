package com.SnHI.server.controller;


import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.pojo.SalaryAdjust;
import com.SnHI.server.service.ISalaryAdjustService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  调薪管理接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Api(tags = "SalaryAdjustController员工调薪管理接口")
@RestController
@RequestMapping("/salary/adjust")
public class SalaryAdjustController {

    @Autowired
    private ISalaryAdjustService salaryAdjustService;

    @ApiOperation(value = "获取所有员工调薪记录")
    @GetMapping("/")
    public List<SalaryAdjust> getAllSalaryAdjust() {
        return salaryAdjustService.getAllSalaryAdjust();
    }

    @ApiOperation(value = "新增员工调薪记录")
    @PostMapping("/")
    public ResponseResult insertSalaryAdjust(@RequestBody SalaryAdjust salaryAdjust) {
        if (salaryAdjustService.save(salaryAdjust)) return new ResponseResult(200, "新增成功！");
        return new ResponseResult(500, "新增失败！");
    }

    @ApiOperation(value = "修改员工调薪记录")
    @PutMapping("/")
    public ResponseResult updateSalaryAdjust(@RequestBody SalaryAdjust salaryAdjust) {
        if (salaryAdjustService.updateById(salaryAdjust)) return new ResponseResult(200, "修改成功！");
        return new ResponseResult(500, "修改失败！");
    }

    @ApiOperation(value = "删除员工调薪记录")
    @DeleteMapping("/{id}")
    public ResponseResult deleteSalaryAdjust(@PathVariable Integer id) {
        if (salaryAdjustService.removeById(id)) return new ResponseResult(200, "删除成功！");
        return new ResponseResult(500, "删除失败！");
    }

    @ApiOperation(value = "批量删除员工调薪记录")
    @DeleteMapping("/")
    public ResponseResult batchDeleteSalaryAdjust(Integer[] ids) {
        if (salaryAdjustService.removeByIds(Arrays.asList(ids))) return new ResponseResult(200, "批量删除成功！");
        return new ResponseResult(500, "批量删除失败！");
    }

}
