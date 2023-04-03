package com.nf.handler;

import com.nf.ResultSetHandler;
import com.nf.handler.row.RowProcessorRealize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 该类实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 List<Object[]> 序列
 */
public class ArrayListHandler<T> implements ResultSetHandler<List<Object[]>> {

    //添加依赖对象 RowProcessor
    private RowProcessor processor;

    /**
     * 无参构造函数
     */
    public ArrayListHandler() {
        //设置默认依赖
        this(new RowProcessorRealize());
    }

    /**
     * 用于设置依赖
     * @param processor 实现依赖对象
     */
    public ArrayListHandler(RowProcessor processor) {
        this.processor = processor;
    }

    /**
     * 该方法返回的对象是一个 @{List<Object[]>}序列
     * @param rs 数据库查询结果集
     * @return @{List<Object[]>} 序列
     * @throws SQLException
     */
    @Override
    public List<Object[]> handler(ResultSet rs) throws SQLException {
        //使用依赖的toArrayList 方法
        return processor.toArrayList(rs);
    }
}
