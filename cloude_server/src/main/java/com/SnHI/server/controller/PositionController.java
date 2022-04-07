package com.SnHI.server.controller;


import com.SnHI.server.mapper.PositionMapper;
import com.SnHI.server.pojo.Position;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IPositionService;
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
@Api(tags = "PositionController职位接口")
@RestController
@RequestMapping("/system/basic/position")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPositions() {
        return positionService.list();
    }

    @ApiOperation(value = "新增职位信息")
    @PostMapping("/")
    public ResponseResult insertPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)) {
            return new ResponseResult(200, "新增成功");
        }
        return new ResponseResult(500, "新增失败");
    }

    @ApiOperation(value = "修改职位信息")
    @PutMapping("/")
    public ResponseResult updatePosition(@RequestBody Position position) {
        if (positionService.updateById(position)) {
            return new ResponseResult(200, "修改成功");
        }
        return new ResponseResult(500, "修改失败");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public ResponseResult deletePositionById(@PathVariable Integer id) {
        if (positionService.removeById(id)) {
            return new ResponseResult(200, "删除成功");
        }
        return new ResponseResult(500, "删除失败");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public ResponseResult deletePositionByIds(Integer[] ids) {
        if (positionService.removeByIds(Arrays.asList(ids))) {
            return new ResponseResult(200, "批量删除成功");
        }
        return new ResponseResult(500, "批量删除失败");
    }

}
