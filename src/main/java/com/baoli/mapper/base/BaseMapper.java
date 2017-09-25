package com.baoli.mapper.base;

import java.util.List;

/************************************************************
 * @author jerry.zheng
 * @Description
 * @date 2017-09-22 9:18
 ************************************************************/
public interface BaseMapper<T, K> {
    List<T> getAll();

    T getOne(Integer id);

    K insert(T user);

    K update(T user);

    void delete(Integer id);
}
