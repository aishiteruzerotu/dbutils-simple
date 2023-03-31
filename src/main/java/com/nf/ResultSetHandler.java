package com.nf;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 该接口方法用于处理数据库查询结果集
 * @param <T> 表示该类是一个泛型类
 */
public interface ResultSetHandler<T> {

    /**
     * 该方法用于处理数据库查询结果集
     * @param rs 数据库查询结果集
     * @return 一个泛型对象
     * @throws SQLException 数据库查询异常
     */
    T handler(ResultSet rs) throws SQLException;
}
