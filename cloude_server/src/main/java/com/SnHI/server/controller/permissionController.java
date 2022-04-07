package com.SnHI.server.controller;

import com.SnHI.server.pojo.Menu;
import com.SnHI.server.pojo.MenuRole;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.pojo.Role;
import com.SnHI.server.service.IMenuRoleService;
import com.SnHI.server.service.IMenuService;
import com.SnHI.server.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限接口
 */
@Api(tags = "permissionController权限接口")
@RequestMapping("/system/basic/permiss")
@RestController
public class permissionController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;


    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/role")
    public ResponseResult addRole(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (roleService.save(role)) {
            return new ResponseResult(200, "添加成功!");
        }
        return new ResponseResult(500, "添加失败!");
    }


    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{rid}")
    public ResponseResult deleteRole(@PathVariable Integer rid) {
        if (roleService.removeById(rid)) {
            return new ResponseResult(200, "删除成功!");
        }
        return new ResponseResult(500, "删除失败!");
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid){
        QueryWrapper<MenuRole> roleQueryWrapper = new QueryWrapper<MenuRole>().eq("rid", rid);
        return menuRoleService.list(roleQueryWrapper).stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public ResponseResult updateMenuRole(Integer rid, Integer[] mids) {
        return menuRoleService.updateMenuRole(rid, mids);
    }

}
