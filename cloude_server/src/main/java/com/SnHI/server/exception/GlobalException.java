package com.SnHI.server.exception;

import com.SnHI.server.pojo.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 处理全局异常
 */
@RestControllerAdvice
public class GlobalException {
    /**
     * 处理所有SQL异常
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public ResponseResult mySQLException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return new ResponseResult(500, "该数据有关联数据，无法操作！");
        }
        return new ResponseResult(500, "数据库操作失败！");
    }
}
