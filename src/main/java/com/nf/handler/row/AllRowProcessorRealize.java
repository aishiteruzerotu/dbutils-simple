package com.nf.handler.row;

import com.nf.handler.AllRowProcessor;
import com.nf.handler.RowProcessor;
import com.nf.handler.row.util.CreateUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类实现了 RowProcessor 接口的方法
 */
public class AllRowProcessorRealize implements AllRowProcessor {

    /**
     * 返回一个 @{List<Object[]>} 数组对象
     * @param rs 结果集
     * @return @{List<Object[]>}
     * @throws SQLException
     */
    @Override
    public List<Object[]> toArrayList(ResultSet rs) throws SQLException {
        //声明 List<Object[]> 序列对象
        List<Object[]> result = new ArrayList<>();
        //循环取值
        while (rs.next()){
            //增加数据
            //createObjects 方法生成添加数据
            result.add(CreateUtils.createObjects(rs));
        }
        //返回 List<Object[]> 序列对象
        return result;
    }

    /**
     * 返回一个 泛型对象 序列
     * 一般为实体类的数组集合
     * @param rs 结果集
     * @param <T> 泛型对象
     * @return @{List<T>} 序列
     * @return 一个实体类对象，包含其数据
     * @throws SQLException
     */
    @Override
    public <T> List<T> toBeanList(ResultSet rs, Class<? extends T> type) throws SQLException {
        return null;
    }

    /**
     * 返回一个由列名为 K(String) 值，数据为 V(Object) 值的  @{List<Map<String,Object>>} 的 list对象
     * @param rs 结果集
     * @return  @{List<Map<String,Object>>} list对象
     * @throws SQLException
     */
    @Override
    public List<Map<String, Object>> toMapList(ResultSet rs) throws SQLException {
        //声明 List<Map<String, Object>> 序列对象
        List<Map<String, Object>> result = new ArrayList<>();
        //循环取值
        while (rs.next()){
            //增加数据
            //createMap 方法生成添加数据
            result.add(CreateUtils.createMap(rs));
        }
        //返回 List<Map<String, Object>> 序列对象
        return result;
    }
}
