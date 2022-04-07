package com.SnHI.server.service;

import com.SnHI.server.mapper.AdminMapper;
import com.SnHI.server.mapper.MenuMapper;
import com.SnHI.server.pojo.Admin;
import com.SnHI.server.pojo.LoginAdmin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Admin admin = adminMapper.selectOne(queryWrapper);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        if (!admin.getEnabled()) {
            throw new UsernameNotFoundException("账号已停用！");
        }
//        admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
//        adminMapper.updateById(admin);
        // 查询相应的权限信息封装进LoginAdmin中
        List<String> permissions = menuMapper.getPermsByUserID(admin.getId());
        // 把数据封装成UserDetails返回
        return new LoginAdmin(admin, permissions);
    }
}
