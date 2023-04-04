package com.nf.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 该类用于实现 Handler 的部分实现类的具体操作
 * 实现功能为单行操作
 * 主要是为列名及值服务
 */
public interface RowProcessor {

    /**
     * 返回一个 Object[] 数组对象
     * @param rs 结果集
     * @return Object[]对象
     * @throws SQLException
     */
    Object[] toArray(ResultSet rs)throws SQLException;

    /**
     * 返回一个 泛型对象 ，默认为实体类对象，为其字段赋值
     * @param rs 结果集
     * @param type 类数据
     * @param <T> 泛型对象
     * @return 一个实体类对象，包含其数据
     * @throws SQLException
     */
    <T> T toBean(ResultSet rs,Class<? extends T> type)throws SQLException;

    /**
     * 返回一个由列名为 K(String) 值，数据为 V(Object) 值的  @{Map<String,Object>} 的Map对象
     * @param rs 结果集
     * @return @{Map<String,Object>} Map对象
     * @throws SQLException
     */
    Map<String,Object> toMap(ResultSet rs)throws SQLException;

}
