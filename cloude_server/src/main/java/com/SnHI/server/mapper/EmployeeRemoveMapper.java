package com.SnHI.server.mapper;

import com.SnHI.server.pojo.EmployeeRemove;
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
public interface EmployeeRemoveMapper extends BaseMapper<EmployeeRemove> {

    /**
     * 获取所有员工调动记录
     * @return
     */
    List<EmployeeRemove> getAllEmployeeRemove();
}
