package com.SnHI.server.service.impl;

import com.SnHI.server.pojo.LoginAdmin;
import com.SnHI.server.pojo.Menu;
import com.SnHI.server.mapper.MenuMapper;
import com.SnHI.server.service.IMenuService;
import com.SnHI.server.utils.RedisCache;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 通过用户id查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusById() {
        LoginAdmin loginAdmin = (LoginAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = loginAdmin.getAdmin().getId();
        List<Menu> menus = redisCache.getCacheObject("menu_" + id);
        if (menus == null) {
            menus = menuMapper.getMenusById(id);
            redisCache.setCacheObject("menu_" + id, menus);
        }
        return menus;
    }

    /**
     * 查询所有菜单信息
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
