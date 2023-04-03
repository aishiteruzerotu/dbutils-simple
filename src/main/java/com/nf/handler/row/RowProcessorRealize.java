package com.nf.handler.row;

import com.nf.handler.RowProcessor;
import com.nf.handler.row.util.CreateUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * 该类实现了 RowProcessor 接口的方法
 */
public class RowProcessorRealize implements RowProcessor {

    /**
     * 返回一个 Object[] 数组对象
     * @param rs 结果集
     * @return Object[]对象
     * @throws SQLException
     */
    @Override
    public Object[] toArray(ResultSet rs) throws SQLException {
        //CreateUtils 的 createObjects 方法生成 Object[] 对象
        return CreateUtils.createObjects(rs);
    }


    /**
     * 返回一个 泛型对象 ，默认为实体类对象，为其字段赋值
     * @param rs 结果集
     * @param <T> 泛型对象
     * @return 一个实体类对象，包含其数据
     * @throws SQLException
     */
    @Override
    public <T> T toBean(ResultSet rs,Class<? extends T> type) throws SQLException {
        //CreateUtils 的 createBean 方法生成 bean 对象
        return CreateUtils.createBean(rs,type);
    }

    /**
     * 返回一个由列名为 K(String) 值，数据为 V(Object) 值的  @{Map<String,Object>} 的Map对象
     * @param rs 结果集
     * @return @{Map<String,Object>} Map对象
     * @throws SQLException
     */
    @Override
    public Map<String, Object> toMap(ResultSet rs) throws SQLException {
        //CreateUtils 的 createMap 方法生成 Map<String, Object> 对象
        return CreateUtils.createMap(rs);
    }

}
