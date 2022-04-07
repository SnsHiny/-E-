package com.SnHI.server.mapper;

import com.SnHI.server.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Repository
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer insertMenuRole(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
