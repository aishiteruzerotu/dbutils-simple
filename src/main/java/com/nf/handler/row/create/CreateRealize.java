package com.nf.handler.row.create;

import com.nf.ReflexException;
import com.nf.handler.row.CreateRow;
import com.nf.handler.row.create.BeanUtils;
import com.nf.util.ResultSetMetaUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 该类用于生成对象
 */
public class CreateRealize implements CreateRow {

    /**
     * 根据结果集的数据，返回一个Object[]对象
     * @param rs 结果集
     * @return Object[]对象
     * @throws SQLException
     */
    public Object[] createObjects(ResultSet rs) throws SQLException {
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
    public Map<String, Object> createMap(ResultSet rs) throws SQLException{
        //声明一个 Map<String, Object> 对象
        Map<String, Object> result = new LinkedHashMap<>();
        //获取 ResultSetMetaData 源数据对象
        ResultSetMetaData metaData = rs.getMetaData();
        //获取列的长度
        int columnCount = metaData.getColumnCount();
        //根据列的长度，从下标 1 开始循环
        for (int i = 1; i <= columnCount; i++) {
            //声明 columnName , 调用工具类 ResultSetMetaUtils 的 方法 getColumnName 得到列名
            String columnName = ResultSetMetaUtils.getColumnName(metaData,i);
            //给map对象赋值
            result.put(columnName,rs.getObject(i));
        }
        //返回 Map<String,Object> 对象 result
        return result;
    }

    /**
     * 用于创建一个对象
     * @param type
     * @param <T>
     * @return
     */
    public <T> T createBean(ResultSet rs,Class<? extends T> type) {
        //声明对象
        T result = null;
        try {
            //创建一个bean对象
            result = type.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            //抛出异常
            throw new ReflexException("fail to create bean,Default constructor is null...",e);
        }
        //调用填充方法，返回一个有数据的 bean 对象
        return BeanUtils.populateBean(rs,result);
    }
}
