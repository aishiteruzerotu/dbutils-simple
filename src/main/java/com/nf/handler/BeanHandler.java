package com.nf.handler;

import com.nf.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 该类实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 实体类
 */
public class BeanHandler<T> implements ResultSetHandler<T> {
    @Override
    public T handler(ResultSet rs) throws SQLException {
        return null;
    }
}
