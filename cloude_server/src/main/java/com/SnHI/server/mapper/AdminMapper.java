package com.SnHI.server.mapper;

import com.SnHI.server.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 获取所有操作员
     * @param keyWords
     * @return
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keyWords") String keyWords);

    /**
     * 获取当前登录用户信息
     * @param userName
     * @return
     */
    Admin getLoginUserInfo(String userName);
}
