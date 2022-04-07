package com.SnHI.server.mapper;

import com.SnHI.server.pojo.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SnHI
 * @since 2022-03-11
 */
@Repository
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    Integer addRoles(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
