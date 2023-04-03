package com.nf.handler;

import com.nf.ResultSetHandler;
import com.nf.handler.row.RowProcessorRealize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 该类实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 @{Map<String,Object>} 对象
 */
public class MapHandled<T> implements ResultSetHandler<Map<String,Object>> {

    //添加依赖对象 RowProcessor
    private RowProcessor processor;

    /**
     * 无参构造函数
     */
    public MapHandled() {
        //设置默认依赖
        this(new RowProcessorRealize());
    }

    /**
     * 用于设置依赖
     * @param processor 实现依赖对象
     */
    public MapHandled(RowProcessor processor) {
        this.processor = processor;
    }

    /**
     * 该方法返回的对象是一个 @{Map<String,Object>} 对象
     * @param rs 数据库查询结果集
     * @return @{Map<String,Object>} 对象
     * @throws SQLException
     */
    @Override
    public Map<String, Object> handler(ResultSet rs) throws SQLException {
        // 三则运算 如果rs为空时，返回 空 null
        return rs.next()? this.processor.toMap(rs) : null;
    }
}