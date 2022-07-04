package com.SnHI.server.mapper;

import com.SnHI.server.pojo.EmployeeEc;
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
public interface EmployeeEcMapper extends BaseMapper<EmployeeEc> {

    /**
     * 获取所有员工奖惩记录
     * @return
     */
    List<EmployeeEc> getAllEmployeeEc();
}
