package com.nf.handler.keyed;

import com.nf.handler.RowProcessor;
import com.nf.handler.row.RowProcessorRealize;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 该类继承了  AbstractKeyedHandler<Map<String>>
 * 该类返回的对象是一个 @{Map<Object, Map<String, Object>>} Map的数据结果集
 * 该类的是由用户指定所需要的列名称或行数，取值给 K ，由该值给 Map对象做 Key
 * K 值所在的行，则会打包成一个 @{Map<String,Object>} 的对象，作为返回对象 Value
 * 由此，该返回的 Map 对象，一条数据包含了数据库查询的一条数据
 */
public class KeyedMapHandler<V> extends AbstractKeyedHandler<Map<String, Object>> {
    /**
     * 默认构造函数
     * 默认取第一行作为 K 值
     */
    public KeyedMapHandler() {
        this(COLUMINDEXDEFAULTVALUE,COLUMNAMEDEFAULTVALUE, DEFAULT_PROCESSOR);
    }

    /**
     * 赋值取 K 值行数
     * @param columIndex 取 K 值行数
     */
    public KeyedMapHandler(Integer columIndex) {
        this(columIndex,COLUMNAMEDEFAULTVALUE,new RowProcessorRealize());
    }

    /**
     * 赋值取 K 值行名称
     * @param columName 取 K 值行名称
     */
    public KeyedMapHandler( String columName) {
        this(COLUMINDEXDEFAULTVALUE,columName, DEFAULT_PROCESSOR);
    }

    /**
     * 设置依赖对象
     * 赋值取 K 值行数
     * @param columIndex 取 K 值行数
     */
    public KeyedMapHandler(Integer columIndex, RowProcessor processor) {
        this(columIndex,COLUMNAMEDEFAULTVALUE,processor);
    }

    /**
     * 设置依赖对象
     * 赋值取 K 值行名称
     * @param columName 取 K 值行名称
     */
    public KeyedMapHandler(String columName,RowProcessor processor) {
        this(COLUMINDEXDEFAULTVALUE,columName,processor);
    }

    /**
     * 行数和列名称不应该同时赋值
     * @param columIndex 行数
     * @param columName 行名称
     * @param processor 实现类对象
     */
    protected KeyedMapHandler( Integer columIndex, String columName,RowProcessor processor) {
        this.processor = processor;
        this.columIndex = columIndex;
        this.columName = columName;
    }


    /**
     * 该方法返回值是一个 Map<String, Object> 的 Map 对象
     * @param rs 数据库查询结果集
     * @return @{Map<String, Object>} 的 Map 对象
     * @throws SQLException
     */
    @Override
    protected Map<String, Object> createValue(ResultSet rs) throws SQLException {
        //调用依赖的 toMap 方法
        return this.processor.toMap(rs);
    }
}
