package com.nf.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 该类继承了 AbstractOneResultSetHandler<T>
 * 实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 Object 数组
 */
public class ArrayHandler<T> extends AbstractOneResultSetHandler<Object[]> {

    //如果数据集没有数据则默认方法一个没有长度的Object数组对象
    private static final Object[] OBJECTS = new Object[0];

    /**
     * 无参构造函数
     */
    public ArrayHandler() {
    }

    /**
     * 用于设置依赖
     * @param processor 实现依赖对象
     */
    public ArrayHandler(RowProcessor processor) {
        this.processor = processor;
    }

    /**
     * 该方法返回一个 Object数组
     *
     * @param rs 数据库查询结果集
     * @return 一个 Object数组
     * @throws SQLException 数据库查询异常
     */
    @Override
    public Object[] handler(ResultSet rs) throws SQLException {
        // 三则运算 如果rs为空时，返回 OBJECTS 对象
        return rs.next() ? this.processor.toArray(rs) : OBJECTS;
    }
}
