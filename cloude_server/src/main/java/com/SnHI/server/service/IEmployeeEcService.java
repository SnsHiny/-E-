package com.SnHI.server.service;

import com.SnHI.server.pojo.EmployeeEc;
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
public interface IEmployeeEcService extends IService<EmployeeEc> {

    /**
     * 获取所有员工奖惩记录
     * @return
     */
    List<EmployeeEc> getAllEmployeeEc();
}
