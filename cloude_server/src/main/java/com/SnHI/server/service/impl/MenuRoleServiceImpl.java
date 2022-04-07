package com.SnHI.server.service.impl;

import com.SnHI.server.pojo.MenuRole;
import com.SnHI.server.mapper.MenuRoleMapper;
import com.SnHI.server.pojo.ResponseResult;
import com.SnHI.server.service.IMenuRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    @Transactional
    public ResponseResult updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        if (mids == null || mids.length == 0) return new ResponseResult(200, "删除成功");
        Integer result = menuRoleMapper.insertMenuRole(rid, mids);
        if (result == mids.length) return new ResponseResult(200, "更新成功");
        return new ResponseResult(500, "更新失败");
    }
}
