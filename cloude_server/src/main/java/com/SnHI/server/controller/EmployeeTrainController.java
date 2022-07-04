package com.SnHI.server.controller;

import com.SnHI.server.pojo.EmployeeTrain;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IEmployeeTrainService;
import com.SnHI.server.service.impl.EmployeeTrainServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  员工培训管理接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Api(tags = "EmployeeTrainController员工培训管理接口")
@RestController
@RequestMapping("/employee/train")
public class EmployeeTrainController {

    @Autowired
    private IEmployeeTrainService employeeTrainService;

    @ApiOperation("获取所有员工培训记录")
    @GetMapping("/")
    public List<EmployeeTrain> getAllEmployeeTrain() {
        return employeeTrainService.getAllEmployeeTrain();
    }

    @ApiOperation("新增员工培训记录")
    @PostMapping("/")
    public ResponseResult insertEmployeeTrain(@RequestBody EmployeeTrain employeeTrain) {
        return employeeTrainService.insertEmployeeTrain(employeeTrain);
    }

    @ApiOperation(("更新员工培训记录"))
    @PutMapping("/")
    public ResponseResult updateEmployeeTrain(@RequestBody EmployeeTrain employeeTrain) {
        if (employeeTrainService.updateById(employeeTrain)) return new ResponseResult(200, "更新成功！");
        return new ResponseResult(500, "更新失败！");
    }

    @ApiOperation("删除员工培训记录")
    @DeleteMapping("/{id}")
    public ResponseResult deleteEmployeeTrain(@PathVariable Integer id) {
        if (employeeTrainService.removeById(id)) return new ResponseResult(200, "删除成功！");
        return new ResponseResult(500, "删除失败！");
    }

    @ApiOperation("批量删除员工培训记录")
    @DeleteMapping("/")
    public ResponseResult batchDeleteTrains(Integer[] ids) {
        if (employeeTrainService.removeByIds(Arrays.asList(ids))) return new ResponseResult(200, "批量删除成功！");
        return new ResponseResult(500, "批量删除失败！");
    }

}
