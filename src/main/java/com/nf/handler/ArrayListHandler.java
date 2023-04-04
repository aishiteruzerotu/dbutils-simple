package com.nf.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 该类继承了 AbstractAllResultSetHandler<T>
 * 实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 List<Object[]> 列表
 */
public class ArrayListHandler<T> extends AbstractAllResultSetHandler<List<Object[]>> {

    /**
     * 无参构造函数
     */
    public ArrayListHandler() {
    }

    /**
     * 用于设置依赖
     * @param processor 实现依赖对象
     */
    public ArrayListHandler(AllRowProcessor processor) {
        this.processor = processor;
    }

    /**
     * 该方法返回的对象是一个 @{List<Object[]>}列表
     * @param rs 数据库查询结果集
     * @return @{List<Object[]>} 列表
     * @throws SQLException
     */
    @Override
    public List<Object[]> handler(ResultSet rs) throws SQLException {
        //使用依赖的toArrayList 方法
        return processor.toArrayList(rs);
    }
}
