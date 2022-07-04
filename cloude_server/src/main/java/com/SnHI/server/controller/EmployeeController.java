package com.SnHI.server.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.SnHI.server.pojo.*;
import com.SnHI.server.service.*;
import com.SnHI.server.utils.FastJsonRedisSerializer;
import com.SnHI.server.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  员工管理接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Api(tags = "EmployeeController员工接口")
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private INationService nationService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IJoblevelService jobLevelService;
    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "获取所有员工信息（分页展示）")
    @GetMapping("/")
    public ResponsePageResult getAllEmployees(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              Employee employee,
                                              LocalDate[] beginDateScope) {
        return employeeService.getAllEmployees(currentPage, size, employee, beginDateScope);
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public ResponseResult insertEmployee(@RequestBody Employee employee) {
        return employeeService.insertEmployee(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public ResponseResult updateEmployee(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) return new ResponseResult(200, "更新成功！");
        return new ResponseResult(500, "更新失败！");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public ResponseResult deleteEmployee(@PathVariable Integer id) {
        if (employeeService.removeById(id)) return new ResponseResult(200, "删除成功！");
        return new ResponseResult(500, "删除失败！");
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nation")
    public List<Nation> getAllNations() {
        return nationService.list();
    }

    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/politicsStatus")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/department")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/jobLevel")
    public List<Joblevel> getAllJobLevels() {
        return jobLevelService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/position")
    public List<Position> getAllPositions() {
        return positionService.list();
    }

    @ApiOperation(value = "获取最大员工编号")
    @GetMapping("/maxWorkID")
    public String getMaxWorkID() {
        return employeeService.getMaxWorkID();
    }

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void ExportEmployees(HttpServletResponse response) {
        List<Employee> employees = employeeService.getAllEmployeesToExport(null);
        // 导出参数
        ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, employees);
        ServletOutputStream out = null;
        try {
            //流形式
            response.setHeader("content-type","application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition", "attachment;filename=" + new String("employee.xls".getBytes("utf-8"), "utf-8"));
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    public ResponseResult importEmployees(MultipartFile file) {
        ImportParams params = new ImportParams();
        // 去掉首行标题
        params.setTitleRows(1);
        List<Nation> nationList = nationService.list();
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        List<Department> departmentList = departmentService.list();
        List<Joblevel> jobLevelList = jobLevelService.list();
        List<Position> positionList = positionService.list();
        try {
            List<Employee> employeeList = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            for (Employee employee: employeeList) {
                employee.setNationId(nationList.get(nationList.indexOf(new Nation(employee.getNation().getName()))).getId());
                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                employee.setDepartmentId(departmentList.get(departmentList.indexOf(new Department(employee.getDepartment().getName()))).getId());
                employee.setJobLevelId(jobLevelList.get(jobLevelList.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
                employee.setPosId(positionList.get(positionList.indexOf(new Position(employee.getPosition().getName()))).getId());
            }
            if (employeeService.saveBatch(employeeList)) return new ResponseResult(200, "导入成功！");
            return new ResponseResult(500, "导入失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseResult(500, "导入失败");
    }

}
