package com.moveon.cloud.entity;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName BaseService
 * @Description TODO
 * @Author huangzh
 * @Date 2025/9/2 15:00
 * @Version 1.0
 */
public abstract class BaseService<D extends BaseDao<T>, T extends BaseEntity> {

    @Autowired
    private D dao;

}
