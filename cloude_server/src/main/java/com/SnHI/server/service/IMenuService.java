package com.SnHI.server.service;

import com.SnHI.server.pojo.Menu;
import com.SnHI.server.pojo.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 通过用户id查询菜单列表
     * @return
     */
    List<Menu> getMenusById();

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
