package com.SnHI.server.service.impl;

import com.SnHI.server.pojo.SalaryAdjust;
import com.SnHI.server.mapper.SalaryAdjustMapper;
import com.SnHI.server.service.ISalaryAdjustService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
@Service
public class SalaryAdjustServiceImpl extends ServiceImpl<SalaryAdjustMapper, SalaryAdjust> implements ISalaryAdjustService {

    @Autowired
    private SalaryAdjustMapper salaryAdjustMapper;

    /**
     * 获取所有员工股调薪记录
     * @return
     */
    @Override
    public List<SalaryAdjust> getAllSalaryAdjust() {
        return salaryAdjustMapper.getAllSalaryAdjust();
    }
}
