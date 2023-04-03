package com.nf.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AllRowProcessor {

    /**
     * 返回一个 @{List<Object[]>} 数组对象
     * @param rs 结果集
     * @return @{List<Object[]>}
     * @throws SQLException
     */
    List<Object[]> toArrayList(ResultSet rs)throws SQLException;

    /**
     * 返回一个 泛型对象 序列
     * 一般为实体类的数组集合
     * @param rs 结果集
     * @param <T> 泛型对象
     * @return @{List<T>} 序列
     * @return 一个实体类对象，包含其数据
     * @throws SQLException
     */
    <T> List<T> toBeanList(ResultSet rs, Class<? extends T> type) throws SQLException;

    /**
     * 返回一个由列名为 K(String) 值，数据为 V(Object) 值的  @{List<Map<String,Object>>} 的 list对象
     * @param rs 结果集
     * @return  @{List<Map<String,Object>>} list对象
     * @throws SQLException
     */
    List<Map<String,Object>> toMapList(ResultSet rs)throws SQLException;
}
