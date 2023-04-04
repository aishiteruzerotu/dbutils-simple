package com.nf.handler;

import com.nf.handler.row.AllRowProcessorRealize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 该类实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 @{List<T>} 对象
 */
public class BeanListHandler<T> extends AbstractAllResultSetHandler<List<T>> {

    //返回类型
    private Class<? extends T> type;

    /**
     * 至少要有 类对象 作为参数
     * @param type 类对象
     */
    public BeanListHandler(Class<? extends T> type) {
        //设置默认对象
        this(type,new AllRowProcessorRealize());
    }

    /**
     * 至少要有 类对象 作为参数
     * @param type 类对象
     * @param processor 依赖对象
     */
    public BeanListHandler(Class<? extends T> type, AllRowProcessor processor) {
        this.type = type;
        this.processor = processor;
    }

    /**
     * 该方法返回一个 List<T> 的列表对象
     * @param rs 数据库查询结果集
     * @return @{List<T>} 列表对象
     * @throws SQLException
     */
    @Override
    public List<T> handler(ResultSet rs) throws SQLException {
        //使用依赖的 toBeanList 方法
        return this.processor.toBeanList(rs,this.type);
    }
}

