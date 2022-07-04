package com.SnHI.server.controller;

import com.SnHI.server.pojo.Admin;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * <p>
 *  登录管理接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Api(tags = "LoginController登录接口")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登陆之后返回token")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody Admin admin, HttpServletRequest request) {
        return adminService.login(admin.getUsername(), admin.getPassword(), admin.getCode(), request);
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("/out")
    public ResponseResult logout() {
        return adminService.logout();
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/info")
    public Admin getLoginUserInfo(Principal principal) {
        if (principal == null) return null;
        String userName = principal.getName();
        Admin admin = adminService.getLoginUserInfo(userName);
        return admin;
    }

}
