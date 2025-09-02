package com.moveon.cloud.entity;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName BaseDao
 * @Description TODO
 * @Author huangzh
 * @Date 2025/9/2 14:50
 * @Version 1.0
 */
public interface BaseDao<T>{

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    T get(Long id);

    /**
     * 根据条件查询
     * @param entity
     * @return
     */
    List<T> list(T entity);

    /**
     * 根据ids查询列表
     * @param ids
     * @return
     */
    List<T> listByIds(@Param("ids") List<Long> ids);

    /**
     * 新增数据
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 批量新增
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<T> list);

    /**
     * 修改数据
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 批量修改
     * @param list
     * @return
     */
    int batchUpdate(@Param("list") List<T> list);

    /**
     * 通过ids批量修改数据
     * @param entity
     * @return
     */
    int batchUpdateByIds(T entity);

    /**
     * 删除数据
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据ids批量删除数据
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}
