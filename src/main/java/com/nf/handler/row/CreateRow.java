package com.nf.handler.row;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 该类用于生成对象
 */
public interface CreateRow {

    /**
     * 根据结果集的数据，返回一个Object[]对象
     * @param rs 结果集
     * @return Object[]对象
     * @throws SQLException
     */
    Object[] createObjects(ResultSet rs) throws SQLException;

    /**
     * 根据结果集的数据，返回一个 @{Map<String,Object>} Map对象
     * @param rs 结果集
     * @return @{Map<String,Object>} Map对象
     * @throws SQLException
     */
    Map<String, Object> createMap(ResultSet rs) throws SQLException;

    /**
     * 用于创建一个对象
     * @param type
     * @param <T>
     * @return
     */
    <T> T createBean(ResultSet rs,Class<? extends T> type);
}
