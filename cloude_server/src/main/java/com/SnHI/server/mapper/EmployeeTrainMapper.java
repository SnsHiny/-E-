package com.SnHI.server.mapper;

import com.SnHI.server.pojo.EmployeeTrain;
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
public interface EmployeeTrainMapper extends BaseMapper<EmployeeTrain> {

    /**
     * 获取所有员工培训记录
     * @return
     */
    List<EmployeeTrain> getAllEmployeeTrain();
}
