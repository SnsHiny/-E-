package com.SnHI.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "TestController测试接口")
@RestController
public class TestController {

    @ApiOperation(value = "token测试")
    @GetMapping("/hello")
    public String Hello() {
        return "hello";
    }

    @ApiOperation(value = "权限测试1")
    @PreAuthorize("hasAuthority('基本资料')")
    @GetMapping("/employee/basic/test")
    public String testPerms1() {
        return "/employee/basic/test";
    }

    @ApiOperation(value = "权限测试2")
    @PreAuthorize("hasAuthority('员工资料')")
    @GetMapping("/personnel/emp/test")
    public String testPerms2() {
        return "/personnel/emp/test";
    }

}
