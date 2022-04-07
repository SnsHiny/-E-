package com.SnHI.server.service;

import com.SnHI.server.pojo.Admin;
import com.SnHI.server.pojo.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SnHI
 * @since 2022-03-01
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 用户登录
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    ResponseResult login(String username, String password, String code, HttpServletRequest request);

    /**
     * 用户退出登录
     * @return
     */
    ResponseResult logout();

    /**
     * 获取所有操作员
     * @param keyWords
     * @return
     */
    List<Admin> getAllAdmins(String keyWords);

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    ResponseResult updateRoles(Integer adminId, Integer[] rids);

    /**
     * 更新密码
     * @param oldPass
     * @param pass
     * @param adminId
     * @return
     */
    ResponseResult updatePassword(String oldPass, String pass, Integer adminId);

    /**
     * 更新用户头像
     * @param fileUrl
     * @param id
     * @param authentication
     * @return
     */
    ResponseResult updateUserFace(String fileUrl, Integer id, Authentication authentication);

    /**
     * 获取当前登录用户信息
     * @param userName
     * @return
     */
    Admin getLoginUserInfo(String userName);
}
