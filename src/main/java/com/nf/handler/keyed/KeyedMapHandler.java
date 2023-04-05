package com.nf.handler.keyed;

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
