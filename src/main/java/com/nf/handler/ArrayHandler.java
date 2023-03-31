package com.nf.handler;

import com.nf.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 该类实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 Object 数组
 */
public class ArrayHandler implements ResultSetHandler<Object[]> {

    /**
     * 该方法返回一个 Object数组
     * @param rs 数据库查询结果集
     * @return 一个 Object数组
     * @throws SQLException 数据库查询异常
     */
    @Override
    public Object[] handler(ResultSet rs) throws SQLException {
        //判断rs是否有下一条数据，即判断rs是否为空
        if (rs.next()) {
            //获取数据源
            ResultSetMetaData metaData = rs.getMetaData();
            //获取原数据的列的长度
            int columnCount = metaData.getColumnCount();
            //声明Object 数组 对象
            Object[] objects = new Object[columnCount];
            //循环给Object 数组 对象赋值
            for (int i = 0; i < columnCount; i++) {
                //给Object 数组对象赋值
                objects[i] = rs.getObject(i+1);
            }
            //返回数组对象
            return objects;
        }
        //rs为空时，返回null
        return null;
    }
}
