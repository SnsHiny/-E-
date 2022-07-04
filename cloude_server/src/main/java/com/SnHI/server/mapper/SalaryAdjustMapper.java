package com.SnHI.server.mapper;

import com.SnHI.server.pojo.SalaryAdjust;
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
public interface SalaryAdjustMapper extends BaseMapper<SalaryAdjust> {

    /**
     * 获取所有员工股调薪记录
     * @return
     */
    List<SalaryAdjust> getAllSalaryAdjust();
}
