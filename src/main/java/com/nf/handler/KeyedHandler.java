package com.nf.handler;

import com.nf.handler.row.RowProcessorRealize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

    protected Object createKey(ResultSet rs) throws SQLException {
        Object result = null;
        if (this.columName == null|| columName.isEmpty()){
            result = rs.getObject(this.columIndex);
        }else {
            result = rs.getObject(this.columName);
        }
        return result;
    }

    @Override
    public Map<Object, Map<String, Object>> handler(ResultSet rs) throws SQLException {
        Map<Object, Map<String, Object>> result = new HashMap<>();
        while (rs.next()){
            result.put(createKey(rs),this.processor.toMap(rs));
        }
        return result;
    }
}
