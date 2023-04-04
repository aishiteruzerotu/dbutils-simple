package com.nf.handler;

import com.nf.handler.row.RowProcessorRealize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 该类继承了  AbstractOneResultSetHandler<T>
 * 实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 @{Map<Object, Map<String, Object>>} Map的数据结果集
 * 该类的是由用户指定所需要的列名称或行数，取值给 K ，由该值给 Map对象做 Key
 * K 值所在的行，则会打包成一个 @{Map<String,Object>} 的对象，作为返回对象 Value
 * 由此，该返回的 Map 对象，一条数据包含了数据库查询的一条数据
 */
public class KeyedHandler<T> extends AbstractOneResultSetHandler<Map<Object,Map<String,Object>>> {
    //取 K 值行数
    private Integer columIndex;
    //取 K 值行名称
    private String columName;

    /**
     * 默认构造函数
     * 默认取第一行作为 K 值
     */
    public KeyedHandler() {
        this(1,null,new RowProcessorRealize());
    }

    /**
     * 赋值取 K 值行数
     * @param columIndex 取 K 值行数
     */
    public KeyedHandler(Integer columIndex) {
        this(columIndex,null,new RowProcessorRealize());
    }

    /**
     * 赋值取 K 值行名称
     * @param columName 取 K 值行名称
     */
    public KeyedHandler( String columName) {
        this(1,columName,new RowProcessorRealize());
    }

    /**
     * 设置依赖对象
     * 赋值取 K 值行数
     * @param columIndex 取 K 值行数
     */
    public KeyedHandler(Integer columIndex,RowProcessor processor) {
        this(columIndex,null,processor);
    }

    /**
     * 设置依赖对象
     * 赋值取 K 值行名称
     * @param columName 取 K 值行名称
     */
    public KeyedHandler(String columName,RowProcessor processor) {
        this(1,columName,processor);
    }

    /**
     * 行数和列名称不应该同时赋值
     * @param columIndex 行数
     * @param columName 行名称
     */
    private KeyedHandler( Integer columIndex, String columName,RowProcessor processor) {
        this.processor = processor;
        this.columIndex = columIndex;
        this.columName = columName;
    }

    /**
     * 生成 K 值
     * @param rs 结果集
     * @return K 值
     * @throws SQLException 数据库操作异常
     */
    protected Object createKey(ResultSet rs) throws SQLException {
        //声明 result  K 值
        Object result = null;
        //判断 当前对象 this.columName 是否为空
        if (this.columName == null|| columName.isEmpty()){
            //为空则按 取值 行数 赋值给 K
            result = rs.getObject(this.columIndex);
        }else {
            //不为空则按 取值 行名称 赋值给 K
            result = rs.getObject(this.columName);
        }
        //返回对象 K的值 result
        return result;
    }

    /**
     * 返回一个 Map 对象，包含数据库查询到的数据
     * @param rs 数据库查询结果集
     * @return @{Map<Object, Map<String, Object>>} Map的数据结果集
     * @throws SQLException 数据库操作异常
     */
    @Override
    public Map<Object, Map<String, Object>> handler(ResultSet rs) throws SQLException {
        //声明 Map<Object, Map<String, Object>> 对象 result
        Map<Object, Map<String, Object>> result = new LinkedHashMap<>();
        while (rs.next()){
            //给对象 result 赋值
            result.put(createKey(rs),this.processor.toMap(rs));
        }
        //返回对象 result
        return result;
    }
}
