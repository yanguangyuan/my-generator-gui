package com.learn.ygy.other;

import java.util.List;

/**
 * @Author yanguangyuan
 * @Description 基础dao
 * @createTime 2020年12月31日 13:40:00
 */
public interface BaseDao<T,L> {

    /**
     * 通过id查询
     * @param id
     * @return
     */
    T selectById(L id);

    /**
     * 通过id更新
     * @param t
     * @return
     */
    void updateById(T t);

    /**
     * 批量更新
     * @param list
     */
    void updateBatch(List<T> list);

    /**
     * 通过id删除
     * @param id
     * @return
     */
    void deleteById(L id);

    /**
     * 添加
     * @param t
     */
    void insert(T t);

    /**
     * 批量添加
     * @param list
     */
    void insertBatch(List<T> list);

    /**
     * 通过对象查询列表
     * @param t
     * @return
     */
    List<T> selectList(T t);
}
