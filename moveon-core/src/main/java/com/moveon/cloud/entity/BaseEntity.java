package com.moveon.cloud.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.*;

/**
 * @ClassName BaseEntity
 * @Description TODO
 * @Author huangzh
 * @Date 2025/9/2 14:01
 * @Version 1.0
 */

@Data
public class BaseEntity {

    @Setter(AccessLevel.NONE)
    private Map<String, Class> tableFieldMap = new HashMap<>();

    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 批量查询时使用
     */
    private List<Long> ids;
}
