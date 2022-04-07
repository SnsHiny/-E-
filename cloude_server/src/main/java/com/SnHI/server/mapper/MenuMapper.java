package com.SnHI.server.mapper;

import com.SnHI.server.pojo.Menu;
import com.SnHI.server.pojo.ResponseResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id查询菜单列表
     * @param id
     * @return
     */
    List<Menu> getMenusById(Integer id);

    List<String> getPermsByUserID(Integer id);

    List<Menu> getAllMenus();
}
