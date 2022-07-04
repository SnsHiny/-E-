package com.SnHI.server.controller;


import com.SnHI.server.fdfs.FastDFSClientWrapper;
import com.SnHI.server.pojo.Admin;
import com.SnHI.server.pojo.LoginAdmin;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.pojo.Role;
import com.SnHI.server.service.IAdminService;
import com.SnHI.server.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  用户管理接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Api(tags = "AdminController用户接口")
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keyWords) {
        return adminService.getAllAdmins(keyWords);
    }

    @ApiOperation(value = "更新操作员")
    @PutMapping("/")
    public ResponseResult updateAdmins(@RequestBody Admin admin) {
        if (adminService.updateById(admin)) return new ResponseResult(200, "更新成功！");
        return new ResponseResult(500, "更新失败！");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public ResponseResult deleteAdmins(@PathVariable Integer id) {
        if (adminService.removeById(id)) return new ResponseResult(200, "删除成功！");
        return new ResponseResult(500, "删除失败！");
    }

    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/info")
    public ResponseResult updateAdmin(@RequestBody Admin admin, Authentication authentication) {
        //更新成功，重新构建Authentication对象
        if (adminService.updateById(admin)) {
            LoginAdmin loginAdmin = (LoginAdmin) authentication.getPrincipal();
            loginAdmin.setAdmin(admin);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginAdmin, null, authentication.getAuthorities()));
            return new ResponseResult(200, "更新成功！");
        }
        return new ResponseResult(500, "更新失败！");
    }

    @ApiOperation(value = "更新密码")
    @PutMapping("/pass")
    public ResponseResult updatePassword(@RequestBody Map<String, Object> info) {
        String oldPass = (String) info.get("oldPass");
        String pass = (String)info.get("pass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updatePassword(oldPass, pass, adminId);
    }

    @ApiOperation(value = "更新用户头像")
    @PostMapping("/userface")
    public ResponseResult updateUserFace(MultipartFile file, Integer id, Authentication authentication) throws IOException {
        String fileUrl = fastDFSClientWrapper.uploadFile(file);
        return adminService.updateUserFace(fileUrl, id, authentication);
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/role")
    public ResponseResult updateRoles(Integer adminId, Integer[] rids) {
        return adminService.updateRoles(adminId, rids);
    }
}
