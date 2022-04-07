package com.SnHI.server.service;

import com.SnHI.server.pojo.MenuRole;
import com.SnHI.server.pojo.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
public interface IMenuRoleService extends IService<MenuRole> {

    ResponseResult updateMenuRole(Integer rid, Integer[] mids);
}
