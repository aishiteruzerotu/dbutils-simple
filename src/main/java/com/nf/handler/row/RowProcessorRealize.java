package com.nf.handler.row;

import com.nf.handler.RowProcessor;

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
        //调用自身 createObjects 方法生成 Object[] 对象
        return this.createObjects(rs);
    }

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
            result.add(this.createObjects(rs));
        }
        //返回 List<Object[]> 序列对象
        return result;
    }

    /**
     * 返回一个 泛型对象 ，默认为实体类对象，为其字段赋值
     * @param rs 结果集
     * @param <T> 泛型对象
     * @return 一个实体类对象，包含其数据
     * @throws SQLException
     */
    @Override
    public <T> T toBean(ResultSet rs) throws SQLException {
        return null;
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
     * 返回一个由列名为 K(String) 值，数据为 V(Object) 值的  @{Map<String,Object>} 的Map对象
     * @param rs 结果集
     * @return @{Map<String,Object>} Map对象
     * @throws SQLException
     */
    @Override
    public Map<String, Object> toMap(ResultSet rs) throws SQLException {
        //调用自身 createMap 方法生成 Map<String, Object> 对象
        return this.createMap(rs);
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
            result.add(this.createMap(rs));
        }
        //返回 List<Map<String, Object>> 序列对象
        return result;
    }


    /**
     * 根据结果集的数据，返回一个Object[]对象
     * @param rs 结果集
     * @return Object[]对象
     * @throws SQLException
     */
    private Object[] createObjects(ResultSet rs) throws SQLException {
        //获取数据源
        ResultSetMetaData metaData = rs.getMetaData();
        //获取原数据的列的长度
        int columnCount = metaData.getColumnCount();
        //声明Object 数组 对象
        Object[] objects = new Object[columnCount];
        //循环给Object 数组 对象赋值
        for (int i = 0; i < columnCount; i++) {
            //给Object 数组对象赋值
            objects[i] = rs.getObject(i + 1);
        }
        //返回数组对象
        return objects;
    }


    /**
     * 根据结果集的数据，返回一个 @{Map<String,Object>} Map对象
     * @param rs 结果集
     * @return @{Map<String,Object>} Map对象
     * @throws SQLException
     */
    private Map<String, Object> createMap(ResultSet rs) throws SQLException{
        //声明一个 Map<String, Object> 对象
        Map<String, Object> result = new HashMap<>();
        //获取 ResultSetMetaData 源数据对象
        ResultSetMetaData metaData = rs.getMetaData();
        //获取列的长度
        int columnCount = metaData.getColumnCount();
        //根据列的长度，从下标 1 开始循环
        for (int i = 1; i <= columnCount; i++) {
            //获取别名 getColumnLabel
            //声明 columnName 接收 得到的别名
            String columnName = metaData.getColumnLabel(i);
            //判断 columnName 是否为空
            if (columnName==null||columnName.isEmpty()){
                //为空则 获取列名
                columnName = metaData.getColumnName(i);
            }
            //判断 columnName 是否为空
            if (columnName==null|| columnName.isEmpty()){
                //为空则获取改行的行号
                columnName=Integer.toString(i);
            }
            //给map对象赋值
            result.put(columnName,rs.getObject(i));
        }
        //返回 Map<String,Object> 对象 result
        return result;
    }

}
