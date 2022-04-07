package com.SnHI.server.service.impl;

import com.SnHI.server.mapper.AdminRoleMapper;
import com.SnHI.server.pojo.Admin;
import com.SnHI.server.mapper.AdminMapper;
import com.SnHI.server.pojo.AdminRole;
import com.SnHI.server.pojo.LoginAdmin;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IAdminService;
import com.SnHI.server.utils.JwtUtil;
import com.SnHI.server.utils.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    private String userId= null;

    /**
     * 登陆之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public ResponseResult login(String username, String password, String code, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (code == null || !code.equals(captcha)) return new ResponseResult(401, "验证码填写错误！");
        // 用Authentication authenticate进行用户认证，并将信息存入security全局
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticateResult = authenticationManager.authenticate(authenticationToken);
        // 认证失败，抛出异常
        if (authenticateResult == null) {
            throw new RuntimeException("登录失败");
        }
        // 认证成功，利用userId生成jwt令牌，并存入ResponseResult返回
        LoginAdmin loginAdmin = (LoginAdmin) authenticateResult.getPrincipal();
        this.userId = loginAdmin.getAdmin().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        // 把完整的用户信息存入redis
        redisCache.setCacheObject("login:" + userId, loginAdmin);
        return new ResponseResult(200, "登陆成功", map );
    }

    /**
     * 退出登录，删除redis当中的userId
     * @return
     */
    @Override
    public ResponseResult logout() {
        // 在redis中删除userId
        redisCache.deleteObject("login:" + this.userId);
        return new ResponseResult(200, "退出成功");
    }

    /**
     * 获取所有操作员
     * @param keyWords
     * @return
     */
    @Override
    public List<Admin> getAllAdmins(String keyWords) {
        LoginAdmin loginAdmin = (LoginAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return adminMapper.getAllAdmins(loginAdmin.getAdmin().getId(), keyWords);
    }

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    public ResponseResult updateRoles(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
        Integer result = adminRoleMapper.addRoles(adminId, rids);
        if (result == rids.length) return new ResponseResult(200, "更新成功！");
        return new ResponseResult(500, "更新失败！");
    }

    /**
     * 更新密码
     * @param oldPass
     * @param pass
     * @param adminId
     * @return
     */
    @Override
    public ResponseResult updatePassword(String oldPass, String pass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 旧密码正确
        if (encoder.matches(oldPass, admin.getPassword())) {
            admin.setPassword(encoder.encode(pass));
            if (1 == adminMapper.updateById(admin)) return new ResponseResult(200, "更新成功！");
            return new ResponseResult(500, "更新失败！");
        }
        return null;
    }

    /**
     * 更新用户头像
     * @param fileUrl
     * @param id
     * @param authentication
     * @return
     */
    @Override
    public ResponseResult updateUserFace(String fileUrl, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(fileUrl);
        if (1 == adminMapper.updateById(admin)) {
            LoginAdmin loginAdmin = (LoginAdmin) authentication.getPrincipal();
            loginAdmin.getAdmin().setUserFace(fileUrl);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginAdmin, null, loginAdmin.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return new ResponseResult(200, "更新成功！");
        }
        return new ResponseResult(500, "更新失败！");
    }

    /**
     * 获取当前登录用户信息
     * @param userName
     * @return
     */
    @Override
    public Admin getLoginUserInfo(String userName) {
        Admin admin = adminMapper.getLoginUserInfo(userName);
        admin.setPassword(null);
        return admin;
    }

}
