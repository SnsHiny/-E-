package com.SnHI.server.controller;

import com.SnHI.server.pojo.Menu;
import com.SnHI.server.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "MenuController菜单接口")
@RequestMapping("/system")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusById() {
        return menuService.getMenusById();
    }

}
