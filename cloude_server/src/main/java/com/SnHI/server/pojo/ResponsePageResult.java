package com.SnHI.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用分页数据返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePageResult {

    private Long total;
    private List<?> data;

}
