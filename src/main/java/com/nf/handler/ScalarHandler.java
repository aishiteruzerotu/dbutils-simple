package com.nf.handler;

import com.nf.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 该类实现了 ResultSetHandler<T> 接口
 * 该类只返回第一行指定一列的数据
 * @param <T>
 */
public class ScalarHandler<T> implements ResultSetHandler<T> {
    //取值行数
    private final int columnIndex;
    //取值行名称
    private final String columnName;

    /**
     * 无参构造函数
     */
    public ScalarHandler() {
        //调用私有的构造函数，默认取第一列的值
        this(1, null);
    }

    /**
     * 设置取值行数
     */
    public ScalarHandler(int columnIndex) {
        this(columnIndex, null);
    }

    /**
     * 设置取值行名称
     */
    public ScalarHandler(String columnName) {
        this(1, columnName);
    }

    /**
     * 最终构造函数，用于赋值
     */
    private ScalarHandler(int columnIndex, String columnName) {
        this.columnIndex = columnIndex;
        this.columnName = columnName;
    }

    /**
     * 返回一行一列的数据
     *
     * @param rs 数据库查询结果集
     * @return 返回第一行指定一列的数据
     * @throws SQLException
     */
    @Override
    public T handler(ResultSet rs) throws SQLException {
        //判断结果集是否为空
        if (rs.next()) {
            //判断取值列名称是否为空
            if (this.columnName != null) {
                //不为空则按列名称取值
                return (T) rs.getObject(this.columnName);
            }
            //为空则按行数取值
            return (T) rs.getObject(this.columnIndex);
        }
        //结果集为空则返回空 null
        return null;
    }
}
