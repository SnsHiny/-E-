package com.SnHI.server.service;

import com.SnHI.server.pojo.SalaryAdjust;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
public interface ISalaryAdjustService extends IService<SalaryAdjust> {

    /**
     * 获取所有员工股调薪记录
     * @return
     */
    List<SalaryAdjust> getAllSalaryAdjust();
}
