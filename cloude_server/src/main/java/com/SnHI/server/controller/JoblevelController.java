package com.SnHI.server.controller;


import com.SnHI.server.pojo.Joblevel;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IJoblevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Api(tags = "JobLevelController职称接口")
@RestController
@RequestMapping("/system/basic/lobLevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;


    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJobLevels(){
        return joblevelService.list();
    }


    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public ResponseResult addJobLevel(@RequestBody Joblevel jobLevel){
        jobLevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(jobLevel)){
            return new ResponseResult(200, "添加成功!");
        }
        return new ResponseResult(500, "删除失败!");
    }


    @ApiOperation(value = "更新职称")
    @PutMapping("/")
    public ResponseResult updateJobLevel(@RequestBody Joblevel jobLevel){
        if (joblevelService.updateById(jobLevel)){
            return new ResponseResult(200, "更新成功!");
        }
        return new ResponseResult(500, "删除失败!");
    }


    @ApiOperation(value = "删除职称")
    @DeleteMapping("/{id}")
    public ResponseResult deleteJobLevel(@PathVariable Integer id){
        if (joblevelService.removeById(id)){
            return new ResponseResult(200, "删除成功!");
        }
        return new ResponseResult(500, "删除失败!");
    }


    @ApiOperation(value = "批量删除职称")
    @DeleteMapping("/")
    public ResponseResult deleteJobLevelByIds(Integer[] ids){
        if (joblevelService.removeByIds(Arrays.asList(ids))){
            return new ResponseResult(200, "批量删除成功!");
        }
        return new ResponseResult(500, "删除失败!");
    }

}
