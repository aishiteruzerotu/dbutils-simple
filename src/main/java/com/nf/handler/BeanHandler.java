package com.nf.handler;

import com.nf.ResultSetHandler;
import com.nf.handler.row.RowProcessorRealize;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 该类实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 实体类
 */
public class BeanHandler<T> implements ResultSetHandler<T> {
    //返回类型
    private Class<? extends T> type;
    //功能依赖 RowProcessor
    private RowProcessor processor;

    /**
     * 至少要有 类对象 作为参数
     * @param type 类对象
     */
    public BeanHandler(Class<? extends T> type) {
        //设置默认对象
        this(type,new RowProcessorRealize());
    }

    /**
     * 至少要有 类对象 作为参数
     * @param type 类对象
     * @param processor 依赖对象
     */
    public BeanHandler(Class<? extends T> type, RowProcessor processor) {
        this.type = type;
        this.processor = processor;
    }

    /**
     * 返回的对象是一个 实体类
     * @param rs 数据库查询结果集
     * @return 泛型实体类
     * @throws SQLException
     */
    @Override
    public T handler(ResultSet rs) throws SQLException {
        // 三则运算 如果rs为空时，返回 空 null
        return rs.next()? this.processor.toBean(rs,this.type):null;
    }
}
