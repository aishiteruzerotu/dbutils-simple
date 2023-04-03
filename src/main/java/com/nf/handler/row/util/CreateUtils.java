package com.nf.handler.row.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类用于生成对象
 */
public class CreateUtils {
    private CreateUtils() {
    }

    /**
     * 根据结果集的数据，返回一个Object[]对象
     * @param rs 结果集
     * @return Object[]对象
     * @throws SQLException
     */
    public static Object[] createObjects(ResultSet rs) throws SQLException {
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
    public static Map<String, Object> createMap(ResultSet rs) throws SQLException{
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
